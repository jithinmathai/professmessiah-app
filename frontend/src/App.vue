<template>
  <v-app>
    <!-- Navbar for Desktop and Mobile -->
    <v-app-bar app color="primary" dark>
      <!-- Navbar Buttons on the Left -->
      <v-btn text to="/">Home</v-btn>
      <v-btn text to="/dashboard">Dashboard</v-btn>
      
     

      <v-spacer></v-spacer>

      <!-- Title aligned to the right -->
      <v-toolbar-title> Profess Messiah 2025 </v-toolbar-title>
    </v-app-bar>

    <!-- Main Content (Router View) -->
    <v-main>
      <v-container>
        <router-view />
        <!-- Dynamically renders the current view (Home or Prayer Requests) -->
      </v-container>
    </v-main>

    <!-- Footer -->
    <v-footer app>
      <v-col class="text-center">© 2024 Profess Messiah</v-col>
    </v-footer>
  </v-app>
</template>

<script setup></script>

<style scoped>
/* Basic styles for App.vue */
.text-center {
  text-align: center;
}

.v-toolbar-title {
  text-align: center;
}
</style>
