<template>
  <div id="eventregistration">
    <h2>Participants</h2>
    <table>
      <tr v-for="participant in participants" >
        <td>{{ participant.name }}</td>
        <td>
          <ul>
            <li v-for="event in participant.events">
              {{event.name}}, {{event.eventDate}}
            </li>
          </ul>
        </td>
      </tr>
      <tr>
        <td>
          <input type="text" v-model="newParticipant" placeholder="Participant Name">
        </td>
        <td>
          <button @click="createParticipant(newParticipant)">Create Participant</button>
        </td>
      </tr>
    </table>
    <p>
      <span v-if="errorParticipant" style="color:red">Error: {{errorParticipant}}</span>
    </p>
    <h2>Events</h2>
    <table>
      <tr v-for="event in events" >
        <td>{{ event.name }}</td>
        <td>{{ event.eventDate }}</td>
        <td>{{ event.startTime }}</td>
        <td>{{ event.endTime }}</td>
      </tr>
      <tr>
        <td>
          <input type="text" v-model="newEvent" placeholder="Event Name">
        </td>
        <td>
          <input type="date" v-model="eventDate">
        </td>
        <td>
          <input type="time" v-model="startTime">
        </td>
        <td>
          <input type="time" v-model="endTime">
        </td>
        <td>
          <button @click="createEvent(newEvent, eventDate, startTime, endTime)">Create Event</button>
        </td>
      </tr>
    </table>
    <p>
      <span v-if="errorEvent" style="color:red">Error: {{errorEvent}}</span>
    </p>
    <h2>Register</h2>
    <table>
      <td>
        <select v-model="selectedParticipant">
          <option disabled value="">Select a participant</option>
          <!--<option v-for="participant in participants">{{ participant.name }}</option>-->
          <option v-for="n in participants.length" v-bind:value="n">{{ participants[n-1].name }}</option>
        </select>
      </td>
      <td>
        <select v-model="selectedEvent">
          <option disabled value="">Select an event</option>
          <option v-for="event in events">{{ event.name }}</option>
        </select>
      </td>
      <td>
        <button @click="register(selectedParticipant, participants[selectedParticipant-1].name, selectedEvent)">Register</button>
      </td>
    </table>
    <p>
      <span v-if="errorRegister" style="color:red">Error: {{errorRegister}}</span>
    </p>
  </div>
</template>
<script src="./registration.js">
</script>
<style>
  #eventregistration {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>
