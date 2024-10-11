<template>
  <div class="intercession-container">
    <v-sheet class="pa-4" elevation="3">
      <h3 class="text-center mb-3 v-typography">Prayer Bank</h3>

      <!-- Form for Counters -->
      <v-form v-model="formIsValid" @submit.prevent="submitForm">
        <v-row class="d-flex justify-center">
          <!-- Temporarily comment out salutation and name fields -->
          <!-- <v-col cols="12" sm="4" md="3">
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
          </v-col> -->
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

        <!-- Submit Button centered at the bottom -->
        <div class="text-center">
          <v-btn :disabled="!formIsValid" type="submit" color="success" class="mt-4 submit-btn">
            Submit
          </v-btn>
        </div>
      </v-form>

      <!-- Button to open the prayer modal -->
      <div class="text-center mt-4">
        <v-btn color="info" @click="openPrayerModal">Profess Messiah Prayer</v-btn>
      </div>

      <!-- Prayer Modal with Language Toggle -->
      <v-dialog v-model="isPrayerModalOpen" max-width="600px">
        <v-card>
          <v-card-title>
            <span>Profess Messiah Prayer</span>
            <v-spacer></v-spacer>
            <v-switch
                v-model="isEnglish"
                :label="isEnglish ? 'English' : 'Malayalam'"
                class="language-switch"
            ></v-switch>
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

      <!-- Success Dialog -->
      <v-dialog v-model="isSuccessDialogOpen" max-width="400px">
        <v-card>
          <v-card-title class="text-h6">Success</v-card-title>
          <v-card-text>Prayers offered successfully!</v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="isSuccessDialogOpen = false">OK</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-sheet>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import apiClient from 'axios'; // Import the Axios instance

// Form State
const name = ref('test');
const salutation = ref('test');
const formIsValid = ref(true);
const isPrayerModalOpen = ref(false);
const isSuccessDialogOpen = ref(false);
const isEnglish = ref(true); // State for language toggle

// Counter Items
const trackerItems = ref([
  { id: 1, label: 'Holy Mass', count: 0 },
  { id: 2, label: 'Creed', count: 0 },
  { id: 3, label: 'Memorare', count: 0 },
  { id: 4, label: 'Rosary', count: 0 },
  { id: 5, label: 'Mercy Chaplet', count: 0 },
  { id: 6, label: 'Profess Messiah Prayer', count: 0 },
  { id: 7, label: 'Adoration Time', count: 0 },
]);

// Computed property for prayer content based on language
const prayerContent = computed(() => {
  return isEnglish.value
      ? `Merciful Lord, we thank you for all the gifts and blessings in our lives, especially remembering our work.\n\nJesus, we offer the Profess Messiah Conference of the Jesus Youth Professional Ministry, which we desire and prepare for, to your Sacred Heart. We pray that, through this conference, the spiritual fervor and new awakening that was present in the early church through the fullness of the Holy Spirit may be experienced by us and all who participate. Help us to go into the world, proclaim the Gospel to all creation, and become your faithful disciples.\n Amen.\n\n Holy Mother Mary,\n pray for the Profess Messiah Conference.\n\n St. Joseph,\n Patron of Workers,\n pray for the Profess Messiah Conference.`
      : `കരുണാമയനായ കർത്താവേ, ഞങ്ങളുടെ ജീവിതത്തിലെ എല്ലാ ദാനങ്ങൾക്കും അനുഗ്രഹങ്ങൾക്കും, പ്രത്യേകിച്ച് ഞങ്ങളുടെ ജോലിയെയും ഓർത്ത് നന്ദി പറയുന്നു.\n\n ഈശോയെ, ഞങ്ങൾ ആഗ്രഹിക്കുകയും പ്രാർത്ഥിച്ചൊരുങ്ങുകയും ചെയ്യുന്ന ജീസസ് യൂത്ത് പ്രൊഫഷണൽ മിനിസ്ട്രിയുടെ Profess Messiah കോൺഫറൻസിനെ അങ്ങയുടെ തിരുഹൃദയത്തിൽ സമർപ്പിക്കുന്നു. ഈ കോൺഫറൻസിലൂടെ, പരിശുദ്ധാത്മനിറവിനാൽ ആദിമ സഭയിലുണ്ടായ ആത്മീയ തീക്ഷണതയും പുത്തൻ ഉണർവ്വും ഞങ്ങൾക്കും പങ്കെടുക്കുന്ന എല്ലാവർക്കുമുണ്ടാകുവാൻ വേണ്ടി പ്രാർത്ഥിക്കുന്നു. ലോകമെങ്ങും പോയി, എല്ലാ സൃഷ്‌ടികളോടും സുവിശേഷം പ്രസംഗിക്കുവാനും, അങ്ങയുടെ ഉത്തമ ശിഷ്യരാകുവാനും ഞങ്ങളെ സഹായിക്കണമേ.\n ആമ്മേൻ.\n\n പരിശുദ്ധ അമ്മേ മാതാവേ,\n Profess Messiah കോൺഫറൻസിനു വേണ്ടി പ്രാർത്ഥിക്കണമേ.\n\n വിശുദ്ധ യൗസേപ്പിതാവേ, തൊഴിലാളികളുടെ മദ്ധ്യസ്ഥാ,\n Profess Messiah കോൺഫറൻസിനു വേണ്ടി പ്രാർത്ഥിക്കണമേ.`;
});

// Increment and decrement functions
const increment = (item) => {
  if (item.label === 'Adoration Time') {
    item.count += 5;
  } else {
    item.count += 1;
  }
};

const decrement = (item) => {
  if (item.count > 0) {
    if (item.label === 'Adoration Time') {
      item.count = Math.max(item.count - 5, 0);
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

// Open and close prayer modal
const openPrayerModal = () => {
  isPrayerModalOpen.value = true;
};

const closePrayerModal = () => {
  isPrayerModalOpen.value = false;
};

const isLoading = ref(false); // State to manage loading indicator

const submitForm = async () => {
  isLoading.value = true; // Start the loader when the form is submitted
  try {
    const payload = {
      salutation: salutation.value,
      name: name.value,
      trackerItems: trackerItems.value.map((item) => ({
        id: item.id,
        label: item.label,
        count: item.count,
      })),
    };

    // Call the backend API using Axios
    await apiClient.post('/api/intercessions/tracker-items', payload);

    console.log('Form submitted successfully');
    isSuccessDialogOpen.value = true; // Open success dialog on successful submission
  } catch (error) {
    console.error('Error submitting form:', error);
  } finally {
    isLoading.value = false; // Stop the loader once the response is received
  }
};
</script>

<style scoped>
/* Styles remain as before, with no changes */
</style>
