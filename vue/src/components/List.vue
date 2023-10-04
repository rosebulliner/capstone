<template>
  <div id="list">
    <div class="title-container">
      <h2 class="list-title">{{ list.listName }}</h2>
      <font-awesome-icon
        class="list-options"
        v-on:click="openListOptionsModal(), (selectedItem = item)"
        icon="ellipsis"
      />
    </div>
    <ol class="cards">
      <li
        v-for="item in list.itemsInList"
        v-bind:key="item.id"
        v-bind:class="{
          completed: item.completed,
        }"
        class="card"
      >
        <div v-on:click="toggleCompleted(item)">
          <p class="item-name">{{ item.itemName }}</p>
        </div>
        <p class="quantity-title">Quantity:</p>
        <p v-on:click="quantityMinus(item)" class="minus">
          <font-awesome-icon icon="fa-solid fa-minus" />
        </p>
        <p class="item-quantity">{{ item.quantity }}</p>
        <p v-on:click="quantityPlus(item)" class="plus">
          <font-awesome-icon icon="plus" />
        </p>
        <p v-on:click="openDeleteModal(), (selectedItem = item)" class="delete">
          <font-awesome-icon icon="xmark" />
        </p>
        <p v-on:click="openEditModal(), (selectedItem = item)" class="edit">
          <font-awesome-icon icon="pen" />
        </p>
      </li>
    </ol>
    <div class="btn-container">
      <button v-on:click="openAddItemModal()" id="add-new-item" class="btn">
        Add New item
      </button>
      <button id="clear-all-btn" class="btn">Clear All</button>
    </div>

    <!-- edit item popup -->
    <vue-modal-2
      class="modal"
      :headerOptions="{
        title: 'Let\'s make some edits :)',
      }"
      lightBg="#EBFFFD"
      :footerOptions="{
        btn1: 'Update',
        disableBtn2: true,
        btn1Style: {
          backgroundColor: '#FFF7C6',
        },
        btn2OnClick: () => {},
        btn1OnClick: () => {
          editItem();
        },
      }"
      :name="'edit-modal-' + this.list.id"
      @on-close="closeEditModal()"
    >
      <form action="">
        <div>
          <div>
            <label for="item-name">Item Name</label>
          </div>
          <input type="text" id="item-name" v-model="selectedItem.itemName" />
        </div>
        <div>
          <label for="item-quantity">Item Quantity</label>
        </div>
        <input
          type="number"
          id="item-quantity"
          v-model.number="selectedItem.quantity"
        />
      </form>
    </vue-modal-2>

    <!-- delete item popup -->
    <vue-modal-2
      class="modal"
      :headerOptions="{
        title: 'WARNING!',
      }"
      lightBg="#EBFFFD"
      :footerOptions="{
        btn1: 'Cancel',
        btn2: 'Delete',
        btn2Style: {
          backgroundColor: '#FFF7C6',
          color: 'black',
        },
        btn2OnClick: () => {
          deleteItem();
        },
        btn1OnClick: () => {
          closeDeleteModal();
        },
      }"
      :name="'delete-modal-' + this.list.id"
      @on-close="closeDeleteModal()"
    >
      Are you sure you want to delete this item?
    </vue-modal-2>

    <!-- claim list popup -->
    <vue-modal-2
      class="modal"
      :headerOptions="{
        title: 'Claim list',
      }"
      lightBg="#EBFFFD"
      :footerOptions="{
        btn1: 'Claim',
        disableBtn2: true,
        btn1Style: {
          backgroundColor: '#FFF7C6',
        },
        btn2OnClick: () => {},
        btn1OnClick: () => {
          $vm2.close('list-options-modal-' + this.list.id);
        },
      }"
      :name="'list-options-modal-' + this.list.id"
      @on-close="closeListOptionsModal()"
    >
      Are you sure you want to claim this list?
    </vue-modal-2>

    <!-- add new Item Modal -->
    <vue-modal-2
      class="modal"
      :headerOptions="{
        title: 'Let\'s add an item to: ' + this.list.listName,
      }"
      lightBg="#EBFFFD"
      :footerOptions="{
        btn1: 'Add',
        disableBtn2: true,
        btn1Style: {
          backgroundColor: '#FFF7C6',
        },
        btn2OnClick: () => {},
        btn1OnClick: () => {
          // add item method calll here
          addItem();
        },
      }"
      :name="'add-item-modal-' + this.list.id"
      @on-close="closeAddItemModal()"
    >
      <form action="">
        <div>
          <div>
            <label for="item-name">Item Name</label>
          </div>
          <input type="text" id="item-name" v-model="selectedItem.itemName" />
        </div>
        <div>
          <label for="item-quantity">Item Quantity</label>
        </div>
        <input
          type="number"
          id="item-quantity"
          v-model.number="selectedItem.quantity"
        />
      </form>
    </vue-modal-2>
  </div>
</template>

<script>
import ItemServices from "../services/ItemServices";
//import ListServices from '../services/ListServices';
export default {
  name: "list",

  props: {
    list: Object,
  },
  data() {
    return {
      isCompleted: false,
      name: "",
      selectedItem: {},
      //list: [],
    };
  },
  methods: {
    toggleCompleted(item) {
      this.$store.commit("SET_ACTIVE_ITEM", item);
      ItemServices.toggleItem(item.id).then((response) => {
        item = response.data;
        this.$store.commit("TOGGLE_COMPLETED", item);
        // this.$router.go(0);
      });
    },
    quantityMinus(item) {
      if (item.quantity > 0) {
        item.quantity = item.quantity - 1;
      }
      ItemServices.updateItem(item).then((response) => {
        if (response.status === 200) {
          this.$store.commit("UPDATE_ITEM", item);
        }
      });
    },
    quantityPlus(item) {
      item.quantity = item.quantity + 1;
      ItemServices.updateItem(item).then((response) => {
        console.log(response.status);
        this.$store.commit("UPDATE_ITEM", item);
      });
    },
    openEditModal() {
      this.$vm2.open("edit-modal-" + this.list.id);
    },
    closeEditModal() {
      this.$vm2.close("edit-modal-" + this.list.id);
    },
    openDeleteModal() {
      this.$vm2.open("delete-modal-" + this.list.id);
    },
    closeDeleteModal() {
      this.$vm2.close("delete-modal-" + this.list.id);
    },
    openListOptionsModal() {
      this.$vm2.open("list-options-modal-" + this.list.id);
    },
    closeListOptionsModal() {
      this.$vm2.close("list-options-modal-" + this.list.id);
    },
    openAddItemModal() {
      this.$vm2.open("add-item-modal-" + this.list.id);
    },
    closeAddItemModal() {
      this.$vm2.close("add-item-modal-" + this.list.id);
    },

    addItem() {
      console.log(this.list);
      this.selectedItem.listId = this.list.id;
      console.log(this.selectedItem);
      ItemServices.addItem(this.selectedItem).then((response) => {
        this.selectedItem = response.data;
        console.log(response.status);
        this.$vm2.close("add-item-modal-" + this.list.id);
        this.$store.commit("ADD_TO_LIST", this.selectedItem);
      });
      //
    },
    deleteItem() {
      // let i = this.list.itemsInList.findIndex(this.list.itemsInList.itemName)

      ItemServices.deleteItem(this.selectedItem.id).then((response) => {
        console.log(response.status);
        console.log("here");
        this.$store.commit("DELETE_FROM_LIST", this.selectedItem);
      });

      this.$vm2.close("delete-modal-" + this.list.id);
    },
    editItem() {
      ItemServices.updateItem(this.selectedItem).then((response) => {
        console.log(response.status);
        console.log(this.selectedItem);
        this.$store.commit("UPDATE_ITEM", this.selectedItem);

        this.$vm2.close("edit-modal-" + this.list.id);
      });
    },
  },
  //   computed:{
  //     completedClass(){
  //       return this.selectedItem.isCompleted? 'completed':'incompleted'
  //     }
  //  }
};
</script>

<style>
.vm2_body[data-v-11fe6c4e] {
  text-align: center;
}

#list-header {
  grid-area: list-header;
}

.title-container {
  display: flex;
  justify-content: space-between;
}

.list-options {
  align-self: center;
  margin-right: 10px;
  cursor: pointer;
  font-size: 30px;
}

#list {
  grid-area: list;
  display: flex;
  flex-direction: column;
  width: 300px;
  margin-right: 10px;
  vertical-align: top;
  background: #7cdedf;
  border-radius: 10px 10px;
  justify-content: flex-start;
  height: fit-content;
  max-height: 500px;
}



::-webkit-scrollbar {
  width: 12px;
  height: 12px;
}

::-webkit-scrollbar-track {
  background: #f5f5f5;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background: #ccc;
}

::-webkit-scrollbar-thumb:hover {
  background: #999;
}

#group-header {
  grid-area: group-header;
}

#group {
  grid-area: group;
}

h1 {
  background-image: linear-gradient(to left, #ebfffd, #b7f7f7);
  width: 100%;
  text-align: center;
  border-radius: 10px 10px;
}
h2 {
  margin: 8px;
  font-size: 1.65em;
  font-weight: bold;
}

.cards {
  display: flex;
  flex-direction: column;
  margin: 0;
  padding: 0;
  overflow: auto;
}

.card {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-template-rows: auto auto;
  grid-template-areas:
    " item-name item-name item-name  . . . . delete"
    "quantity-title . minus item-quantity  plus  . . edit";
  list-style: none;
  background-color: rgb(255, 255, 255);
  font-size: 0.9rem;
  border-radius: 10px;
  margin-bottom: 10px;
  padding: 8px;
  white-space: normal;
  width: 90%;
  margin-left: auto;
  margin-right: auto;

  max-height: 80px;
}

.card:hover{
 background-color: #fff7c6;
}
.item-name {
  grid-area: item-name;
  font-size: 1.3rem;
  margin: 0;
  font-weight: bold;
}

.quantity-title {
  grid-area: quantity-title;
  font-size: 1.2rem;
  margin: 0;
}

.minus {
  grid-area: minus;
  margin: 0 auto 0 auto;
  font-weight: bold;
  cursor: pointer;
}

.item-quantity {
  grid-area: item-quantity;
  margin: auto;
  background-color: #ebfffd;
  border-style: solid;
  border-color: #b7f7f7;
  border-width: 3px;
}

.plus {
  cursor: pointer;
  grid-area: plus;
  margin: 0 auto 0 auto;
  font-weight: bold;
}
.delete {
  cursor: pointer;
  grid-area: delete;
  margin: 0;
  font-weight: bold;
}

.edit {
  cursor: pointer;
  grid-area: edit;
  margin: 0;
  font-weight: bold;
}

.btn-container {
  display: grid;
  grid-template-columns: 1fr 0.25fr 1fr;
  grid-template-areas: "add-new . clear-all";
}

#add-new-item {
  background-color: #fff7c6;
  margin-left: 20px;
  grid-area: add-new;
  font-family: "Happy Monkey", cursive;
  font-weight: bold;
}
#add-new-item:hover {
  background-color: #fde896;
  margin-left: 20px;
  grid-area: add-new;
  font-family: "Happy Monkey", cursive;
  font-weight: bold;
}

#clear-all-btn {
  background-color: #fff7c6;
  margin-right: 20px;
  grid-area: clear-all;
  font-family: "Happy Monkey", cursive;
  font-weight: bold;
}

#clear-all-btn:hover {
  background-color: #fde896;
  margin-right: 20px;
  grid-area: clear-all;
  font-family: "Happy Monkey", cursive;
  font-weight: bold;
}

.btn {
  cursor: pointer;
  margin-bottom: 10px;
  margin-top: 20px;
  background-color: white;
  border-radius: 5px;
  border: none;
  padding: 2px;
}
.incompleted {
  order: 1;
}
.completed {
  order: 2;
}
li.completed {
  text-decoration: line-through;
  background-color: grey;
}
</style>
