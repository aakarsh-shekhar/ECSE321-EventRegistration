import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function ParticipantDto (name) {
  this.name = name
  this.events = []
}

function EventDto (name, date, start, end) {
  this.name = name
  this.eventDate = date
  this.startTime = start
  this.endTime = end
}

export default {
  name: 'eventregistration',
  data () {
    return {
      participants: [],
      newParticipant: '',
      errorParticipant: '',
      events: [],
      newEvent: '',
      eventDate: '',
      startTime: '',
      endTime: '',
      errorEvent: '',
      selectedParticipant: '',
      selectedEvent: '',
      errorRegister: '',
      response: []
    }
  },

  created: function () {
    AXIOS.get('/participants')
    .then(response => {
      // JSON responses are automatically parsed.
      this.participants = response.data
    })
    .catch(e => {
      this.errorParticipant = e;
    });
    AXIOS.get('/events')
    .then(response => {
      // JSON responses are automatically parsed.
      this.events = response.data
    })
    .catch(e => {
      this.errorEvent = e;
    });
  },

  methods: {
  createParticipant: function (participantName) {
    AXIOS.post(`/participants/`+participantName, {}, {})
    .then(response => {
      // JSON responses are automatically parsed.
      this.participants.push(response.data)
      this.newParticipant = ''
      this.errorParticipant = ''
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorParticipant = errorMsg
    });
  },
  createEvent: function (eventName, eventDate, startTime, endTime) {
    AXIOS.post(/events/`+eventName+?date=`+eventDate+`&startTime=`+startTime+`&endTime=`+endTime, {}, {})
    .then(response => {
      // JSON responses are automatically parsed.
      this.events.push(response.data)
      this.newEvent = ''
      this.eventDate = ''
      this.startTime = ''
      this.endTime = ''
      this.errorEvent = ''
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorEvent = errorMsg
    });
  },
  register: function (n, selectedParticipant, selectedEvent) {
    AXIOS.post(`/register?participant=`+selectedParticipant+`&event=`+selectedEvent, {}, {})
    .then(response => {
      // JSON responses are automatically parsed.
      this.participants[n-1] = response.data.participant
      this.selectedParticipant = ''
      this.selectedEvent = ''
      this.errorRegister = ''
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorRegister = errorMsg
    });
  }
  }
}
