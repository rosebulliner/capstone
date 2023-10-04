import axios from "axios";

const http = axios.create({
    baseURL: "http://localhost:9000"});
  

export default {
    addItem(itemToAdd){
        return http.post('/item/add', itemToAdd)
    },
    updateItem(itemToUpdate){
        return http.put('/item/update', itemToUpdate)
    },
    deleteItem(itemID){
        return http.delete(`/item/delete/${itemID}`)
    },
    toggleItem(itemId){
        return http.put(`item/toggle/${itemId}`)
    },
    getListItemDetails(itemId){
        return http.get(`/item/details/${itemId}`)
    },
}   