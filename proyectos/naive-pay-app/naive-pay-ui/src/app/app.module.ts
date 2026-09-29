import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms'; // Importar FormsModule

import { AppComponent } from './app.component';
import { VincularDispositivoComponent } from './devices/components/vincular-dispositivo/vincular-dispositivo.component';
import {CommonModule} from '@angular/common';
import {RouterModule} from "@angular/router";
import {routes} from "./app.routes";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {loggerInterceptor} from "./authentication/service/logger.interceptor";


@NgModule({
  declarations: [
  ],
  imports: [
      RouterModule.forRoot(routes),
    BrowserModule,
    FormsModule, // Agregar FormsModule aquí
    VincularDispositivoComponent,
    AppComponent,
    CommonModule,
    ReactiveFormsModule,
  ],
  providers: [
      {
          provide: HTTP_INTERCEPTORS,
          useFactory: () => loggerInterceptor,
          multi: true
      }
  ],
    exports: [RouterModule]
})
export class AppModule { }
