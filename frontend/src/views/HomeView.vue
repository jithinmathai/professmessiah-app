<template>
  <div class="intercession-container">
    <v-sheet class="pa-4" elevation="3">
      <h3 class="text-center mb-3 v-typography">Prayer Bank</h3>

      <!-- Salutation and Name Fields -->
      <v-form v-model="formIsValid" @submit.prevent="submitForm">
        <v-row class="d-flex justify-center">
          <!-- Salutation Field (Adjusted Size) -->
          <v-col cols="12" sm="4" md="3">
            <v-select
              v-model="salutation"
              :items="salutations"
              label="Salutation"
              :rules="[rules.required]"
              required
              outlined
              dense
              class="adjusted-input"
            ></v-select>
          </v-col>

          <!-- Name Field (Adjusted Size) -->
          <v-col cols="12" sm="8" md="9">
            <v-text-field
              label="Name"
              v-model="name"
              :rules="[rules.required, rules.noEmptySpace]"
              required
              outlined
              dense
              class="adjusted-input"
            ></v-text-field>
          </v-col>
        </v-row>

        <!-- Grid of Counter Items with + and - Buttons -->
        <v-row class="d-flex justify-center counter-grid">
          <v-col v-for="item in trackerItems" :key="item.id" cols="12" sm="6" md="3">
            <v-card class="counter-box" elevation="2">
              <v-card-title class="counter-label">{{ item.label }}</v-card-title>
              <v-card-actions class="counter-controls">
                <v-btn @click="decrement(item)" :disabled="item.count === 0" color="error" small icon>
                  <v-icon>mdi-minus</v-icon>
                </v-btn>
                <div :class="['counter-number', item.label === 'Adoration Time' ? 'adoration-counter' : '']">
                  {{ formatCount(item) }}
                </div>
                <v-btn @click="increment(item)" color="primary" small icon>
                  <v-icon>mdi-plus</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>

        <!-- Button to open the prayer modal -->
        <div class="text-center mt-4">
          <v-btn color="info" @click="openPrayerModal">Profess Messiah Prayer</v-btn>
        </div>

        <!-- Submit Button centered at the bottom -->
        <div class="text-center">
          <v-btn :disabled="!formIsValid" type="submit" color="success" class="mt-4 submit-btn">
            Submit
          </v-btn>
        </div>
      </v-form>

      <!-- Modal for Profess Messiah Prayer with Language Toggle -->
      <v-dialog v-model="isPrayerModalOpen" max-width="500px">
        <v-card>
          <v-card-title class="prayer-title">
            Profess Messiah Prayer
            <v-spacer></v-spacer>
            <!-- Toggle Button for Switching Language -->
            <v-btn icon @click="toggleLanguage">
              <v-icon>{{ language === 'english' ? 'mdi-translate' : 'mdi-translate' }}</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text class="prayer-content">
            {{ prayerContent }}
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="closePrayerModal">Close</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-sheet>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

// Form State
const name = ref('');
const salutation = ref(null);
const formIsValid = ref(false);
const isPrayerModalOpen = ref(false); // State for controlling the prayer modal
const language = ref('english'); // Language state

// List of Salutations for the Dropdown
const salutations = ['Father (Priest)', 'Sister (Nun)', 'Mister', 'Mrs.', 'Miss', 'Master'];

// Validation Rules
const rules = {
  required: (v) => !!v || 'This field is required',
  noEmptySpace: (v) => (v && v.trim() !== '') || 'Name cannot be empty or just spaces',
};

// Counter Items
const trackerItems = ref([
  { id: 1, label: 'Holy Mass', count: 0 },
  { id: 2, label: 'Creed', count: 0 },
  { id: 3, label: 'Memorare', count: 0 },
  { id: 4, label: 'Rosary', count: 0 },
  { id: 5, label: 'Mercy Chaplet', count: 0 },
  { id: 6, label: 'Profess Messiah Prayer', count: 0 },
  { id: 7, label: 'Adoration Time', count: 0 }, // Adoration time starts at 0 minutes
]);

// Computed property to switch between English and Malayalam prayer content
const prayerContent = computed(() => {
  return language.value === 'english'
    ? 'O Jesus, our Lord and Savior, guide us in Your divine truth and light. We profess You as our Messiah and dedicate our prayers and efforts for Your kingdom. Amen.'
    : 'കർത്താവായ യേശുവേ, നമ്മുടെ രക്ഷകനും സൗരഭ്യമായി നിന്റെ ദിവ്യ സത്യത്തിലേക്കും പ്രകാശത്തിലേക്കും ഞങ്ങളെ നയിക്കണമേ. ഞങ്ങൾ നിന്നെ നമ്മുടെ മെസീഹയായി പ്രഖ്യാപിക്കുന്നു, നിന്റെ രാജ്യത്തിനായുള്ള നമ്മുടെ പ്രാർത്ഥനകളും പരിശ്രമങ്ങളും സമർപ്പിക്കുന്നു. ആമേൻ.';
});

// Function to open the prayer modal
const openPrayerModal = () => {
  isPrayerModalOpen.value = true;
};

// Function to close the prayer modal
const closePrayerModal = () => {
  isPrayerModalOpen.value = false;
};

// Function to toggle language
const toggleLanguage = () => {
  language.value = language.value === 'english' ? 'malayalam' : 'english';
};

// Increment Counter
const increment = (item) => {
  if (item.label === 'Adoration Time') {
    item.count += 5; // Add 5 minutes for Adoration Time
  } else {
    item.count += 1;
  }
};

// Decrement Counter (Ensure it doesn't go below 0)
const decrement = (item) => {
  if (item.count > 0) {
    if (item.label === 'Adoration Time') {
      item.count = Math.max(item.count - 5, 0); // Subtract 5 minutes, but not below 0
    } else {
      item.count -= 1;
    }
  }
};

// Format Count for Adoration Time
const formatCount = (item) => {
  if (item.label === 'Adoration Time') {
    const hours = Math.floor(item.count / 60);
    const minutes = item.count % 60;
    return hours > 0 ? `${hours} h ${minutes.toString().padStart(2, '0')} m` : `${minutes} m`;
  }
  return item.count;
};

// Submit Form Handler
const submitForm = () => {
  if (formIsValid.value) {
    console.log('Form Submitted:', {
      salutation: salutation.value,
      name: name.value,
      trackerItems: trackerItems.value,
    });
  }
};
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

/* Counter Controls (Buttons and Number) */
.counter-controls {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  gap: 10px;
}

/* Counter Number Styling */
.counter-number {
  font-size: 1.5rem;
  font-weight: bold;
  margin: 0 10px;
  flex: 1;
  text-align: center;
}

/* Adoration Counter Styling */
.adoration-counter {
  font-size: 1.3rem;
  white-space: nowrap;
}

/* Button Styling */
.v-btn {
  min-width: 36px;
  padding: 6px 10px;
}

/* Submit Button Styling */
.submit-btn {
  font-size: 1rem;
  padding: 10px 24px;
  background-color: #28a745;
  color: #fff;
}

.mt-4 {
  margin-top: 1.5rem;
}
</style>
