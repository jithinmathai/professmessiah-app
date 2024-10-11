<template>
  <div class="intercession-container">
    <v-sheet class="pa-4" elevation="3">
      <h3 class="text-center mb-3 v-typography">Prayer Bank Dashboard</h3>

      <!-- Loader -->
      <div v-if="isLoading" class="loader-container">
        <v-progress-circular indeterminate color="primary" size="70"></v-progress-circular>
      </div>

      <!-- Grid of Total Counts -->
      <v-row v-else class="d-flex justify-center counter-grid">
        <v-col v-for="item in totalCounts" :key="item.id" cols="12" sm="6" md="3">
          <v-card class="counter-box" elevation="2">
            <v-card-title class="counter-label">{{ item.label }}</v-card-title>
            <v-card-text class="text-center display-4">{{ formatCount(item) }}</v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-sheet>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import apiClient from "axios";

// State to hold the total counts
const totalCounts = ref([]);
const isLoading = ref(true); // State to manage loading

// Function to fetch the total counts ( backend call)
const fetchTotalCounts = async () => {
  try {
    // Fetch data from backend API
    const response = await apiClient.get('/api/intercessions/tracker-items');
    totalCounts.value = response.data;
    console.log(response.data);
  } catch (error) {
    console.error('Error fetching total counts:', error);
  } finally {
    isLoading.value = false;
  }
};

// Format the count values for display
const formatCount = (item) => {
  if (item.label === 'Adoration Time') {
    const hours = Math.floor(item.count / 60);
    const minutes = item.count % 60;
    return hours > 0 ? `${hours} h ${minutes.toString().padStart(2, '0')} m` : `${minutes} m`;
  }
  return item.count;
};

// Fetch total counts when the component is mounted
onMounted(() => {
  fetchTotalCounts();
});
</script>

<style scoped>
/* Adjust overall container layout */
.intercession-container {
  padding: 20px;
  background-color: #f7f7f7;
  border-radius: 8px;
}

/* Counter Grid */
.counter-grid {
  margin-top: 20px;
}

/* Loader Container */
.loader-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px; /* Height to center the loader */
}

/* Counter Box for Each Counter */
.counter-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 16px;
  background-color: white;
  border-radius: 8px;
  transition: all 0.3s ease;
  position: relative;
}

.counter-box:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(158, 38, 38, 0.15);
}

/* Counter Label Styling */
.counter-label {
  font-size: 1.2rem;
  margin-bottom: 10px;
  text-align: center;
  font-weight: bold;
}

/* Display the count in large format */
.display-4 {
  font-size: 2.5rem;
  font-weight: bold;
  text-align: center;
}
</style>
