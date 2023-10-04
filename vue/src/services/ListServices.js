import axios from "axios";

//9000 not 8080
//.env files contain port information .enviroment hidden api keys too

// created method which calls axios method to load data from back,
// loads it into variable in data in vue. .then typically goes inside created method but both works.
//components loads into store or then display/filter in component

export default {
  listItems(listID) {
    return axios.get(`list/items/${listID}`);
  },
  listLists() {
    return axios.get(`list/all`);
  },
  newList(list) {
    return axios.post(`list/new`, list);
  },
};
