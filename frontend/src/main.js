// src/main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; // Import the router

import { createVuetify } from 'vuetify';
import 'vuetify/styles';
import '@mdi/font/css/materialdesignicons.css'; // Material Design Icons

const vuetify = createVuetify();

const app = createApp(App);

app.use(router); // Use Vue Router
app.use(vuetify); // Use Vuetify
app.mount('#app');
