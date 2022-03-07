<template>
  <div class="container">
    <h3>main.cpp:</h3>
    <textarea v-model="codeInput" id="codeInput"></textarea>
    <button @click="compile()">Compile and run</button>
    <h3>Output:</h3>
    <div id="ouput">{{ output }}</div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "App",
  data() {
    return {
      codeInput: "",
      output: "",
    };
  },
  methods: {
    compile() {
      const apiClient = axios.create({
        baseURL: "http://localhost:6969",
        withCredentials: false,
        headers: {
          Accept: "text/plain",
          "Content-Type": "text/plain",
        },
      });

      apiClient.post("/compile", this.codeInput).then((response) => {
        this.output = response.data;
      });
    },
  },
};
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  width: 60%;
  margin: auto;
}

#codeInput {
  height: 400px;
}

button {
  width: 200px;
  height: 50px;
  background-color: blue;
  color: white;
  border-radius: 5px;
  border: none;
  margin: auto;
}
button:hover {
  cursor: pointer;
}
button:active {
  background-color: darkblue;
}
</style>
