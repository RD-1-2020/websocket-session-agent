import { Injectable, OnDestroy } from '@angular/core';
import { Client, StompSubscription } from '@stomp/stompjs';
import { SessionHolder } from '@shared/session-holder.service';

@Injectable({
  providedIn: 'root',
})
export class SessionAgentService implements OnDestroy {
  private readonly timeout = 5000;

  private wsUrl = 'ws://127.0.0.1:8443/ws';
  private client = new Client();
  private wsSubscription: StompSubscription;

  isOpen = false;

  constructor(public sessionHolder: SessionHolder) {
    this.client.configure({
      brokerURL: this.wsUrl,
      onConnect: () => {
        console.log('onConnect');
        this.isOpen = true;

        this.wsSubscription = this.client.subscribe(
          '/subject/state',
          (message) => {
            console.log(message);
            this.processMessage(JSON.parse(message.body));
          },
        );

        this.getApiKey();
      },
      debug: (str) => {
        console.log(new Date(), str);
      },
      onStompError: this.errorCallBack,
      onWebSocketError: this.errorCallBack,
      reconnectDelay: this.timeout,
      connectionTimeout: this.timeout,
    });

    console.log('Initialize WebSocket Connection');

    this.client.activate();
  }

  public startHealthCheck() {
    this.sendMessage({
      messageType: 'HEALTH_CHECK',
      data: {
        type: 'healthCheckClientData',
      },
    });
  }

  public getApiKey() {
    this.sendMessage({
      messageType: 'GET',
      data: {
        type: 'getSessionClientData',
      },
    });
  }

  public createNewSession(mfcId: number, windowId: number) {
    this.sendMessage({
      messageType: 'CREATE',
      data: {
        windowId,
        mfcId,
        type: 'createSessionClientData',
      },
    });
  }

  startCheckApiKeyHealthJob() {
    this.startHealthCheck();
  }

  createApiKey(mfcId: number, windowId: number) {
    this.createNewSession(mfcId, windowId);
  }

  ngOnDestroy(): void {
    if (this.client && this.wsSubscription) {
      this.isOpen = false;
      this.wsSubscription.unsubscribe();
      this.client.deactivate().then(() => {
        console.log('Disconnected');
      });
      this.client = null;
    }
  }

  private errorCallBack(error) {
    this.isOpen = false;

    console.log('Something went wrong in work session agent: ' + error);
  }

  private sendMessage(message) {
    this.client.publish({
      body: JSON.stringify(message),
      destination: '/session/ws',
    });
  }

  private processMessage(message) {
    const errorMessage = message.errorMessage;
    if (errorMessage) {
      console.log('Something went wrong in ws session agent: ' + errorMessage);
      return;
    }

    switch (message.messageType) {
      case 'GET':
        console.log(message.data.apiKey);
        this.sessionHolder.apikey = message.data.apiKey;
        this.startCheckApiKeyHealthJob();
        break;
      case 'CREATE':
        this.getApiKey();
        break;
      case 'HEALTH_CHECK':
        console.log(
          'Connection is ' +
            message.data.serverAccessible +
            ' api key is ' +
            message.data.valid,
        );

        if (message.data.valid) {
          setTimeout(() => this.startHealthCheck(), this.timeout);
        } else {
          this.sessionHolder.apikey = null;
        }
    }
  }
}
