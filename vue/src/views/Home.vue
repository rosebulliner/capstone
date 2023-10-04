<template>
  <body id="home-body">
    <div class="home">
      <h1 id="list-header">My Lists</h1>
      <div class="list-and-btn-container">
        <div id="list-container">
          <list
            v-for="list in $store.state.lists"
            v-bind:list="list"
            v-bind:key="list.id"
            class="list"
          />
        </div>
        <button v-on:click="openAddListModal()" id="addList">
          Create New List
        </button>
      </div>
      <h1 id="group-header">My Groups</h1>
      <div class="group-and-btn-container">
        <!-- on clcik on below buttons to add filter list by group functionality -->
        <div id="group-container">
          <group id="group-card"
            v-for="group in groups"
            v-bind:group="group"
            v-bind:key="group.name"
            class="group"
          />
        </div>
        <div class="create-join-btn-container">
          <button v-on:click="openAddGroupModal()" class="button" id="addGroup">
            Create New Group
          </button>
          <button
            v-on:click="openJoinGroupModal()"
            class="button"
            id="joinGroup"
          >
            Join New Group
          </button>
        </div>
      </div>
      <div>
        <vue-modal-2
          class="modal"
          :headerOptions="{
            title: 'To share copy the link in the field below below',
          }"
          lightBg="#EBFFFD"
          :footerOptions="{
            btn1: 'Join',
            disableBtn2: true,
            btn1Style: {
              backgroundColor: '#FFF7C6',
            },

            btn1OnClick: () => {
              //join group functionality here
              $vm2.close('join-group-modal'); // move this line into join method
            },
          }"
          :name="'join-group-modal'"
          @on-close="closeJoinGroupModal()"
        >
          <form action="">
            <div>
              <div>
                <label for="group-code">Group Code </label>
              </div>
              <input type="text" id="group-code" />
            </div>
          </form>
        </vue-modal-2>

        <!-- add list popup -->
        <vue-modal-2
          class="modal"
          :headerOptions="{
            title: 'Let\'s create a list :)',
          }"
          lightBg="#EBFFFD"
          :footerOptions="{
            btn1: 'Create',
            disableBtn2: true,
            btn1Style: {
              backgroundColor: '#FFF7C6',
            },
            btn2OnClick: () => {},
            btn1OnClick: () => {
              addList();
            },
          }"
          :name="'add-list-modal'"
          @on-close="closeAddListModal()"
        >
          <form action="">
            <div>
              <div>
                <label for="list-name">List Name</label>
              </div>
              <input type="text" id="list-name" v-model="newList.listName" />
            </div>
          </form>
        </vue-modal-2>

        <!-- add group popup -->
        <vue-modal-2
          class="modal"
          :headerOptions="{
            title: 'Let\'s create a group :)',
          }"
          lightBg="#EBFFFD"
          :footerOptions="{
            btn1: 'Create',
            disableBtn2: true,
            btn1Style: {
              backgroundColor: '#FFF7C6',
            },
            btn2OnClick: () => {},
            btn1OnClick: () => {
              // create group method call here
              $vm2.close('add-group-modal');
            },
          }"
          :name="'add-group-modal'"
          @on-close="closeAddGroupModal()"
        >
          
          <form action="">
            <div>
              <div>
                <label for="group-name">Group Name</label>
              </div>
              <input type="text" id="group-name" />
            </div>
          </form>
        </vue-modal-2>
      </div>
    </div>
  </body>
</template>

<script>
import ListServices from "../services/ListServices";
import List from "../components/List.vue";
import Group from "../components/Group.vue";
// import ItemServices from "../services/ItemServices";
export default {
  components: { List, Group },
  name: "home",
  data() {
    return {
      groups: [
        {
          name: "Home",
        },
        {
          name: "Work",
        },
      ],
      lists: [],
      newList: {},
      //isCompleted: true,
    };
    //this.lists refers to the data variable list 39>47
  },
  //eventually we would change this method to list by userID or something
  created() {
    ListServices.listLists().then((response) => {
      //write to mutations in store
      this.lists = response.data;
      //this.$store.commit("SAVE_LIST", this.lists); move to bottom :)
      //populate items by list
      for (let list of this.lists) {
        ListServices.listItems(list.id).then((altResponse) => {
          list.itemsInList = altResponse.data;
          // for (let item of list.itemsInList) {
          //   ItemServices.getListItemDetails(item.id).then((lastResponse) => {
          //     item.isCompleted = lastResponse.data.completed;
          //     item.listId = lastResponse.data.listId;
        });
      }
      this.$store.commit("SAVE_LIST", this.lists);
      // });
      // }
    });
    // }
    // });
  },
  methods: {
    openJoinGroupModal() {
      this.$vm2.open("join-group-modal");
    },
    closeJoinGroupModal() {
      this.$vm2.close("join-group-modal");
    },
    openAddListModal() {
      this.$vm2.open("add-list-modal");
    },
    closeAddListModal() {
      this.$vm2.close("add-list-modal");
    },
    openAddGroupModal() {
      this.$vm2.open("add-group-modal");
    },
    closeAddGroupModal() {
      this.$vm2.close("add-group-modal");
    },
    addList() {
      console.log(this.newList);
      //can add group param here poss?idk.
      ListServices.newList(this.newList).then((response) => {
        this.newList = response.data;
        console.log(response.data, response.status);
        this.$store.commit("ADD_NEW_LIST", this.newList);
        this.$router.go(0);
      });
      this.$vm2.close("add-list-modal");
    },
  },
};
</script>
<style scoped>
body {
  width: 100vw;
  
}
.home {
  padding-right: 2%;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  grid-template-areas:
    ". list-header list-header list-header ."
    "list list list list list"
    ". group-header group-header group-header ."
    "group group group group group";
}

#list-header {
  grid-area: list-header;
}

#list-container {
  display: flex;
  flex-direction: row;
  max-height: 500px;
}
.list-and-btn-container {
  grid-area: list;
  flex-wrap: wrap;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

#group-container {
  justify-content: space-between;

  display: flex;
  flex-direction: row;
  max-height: 500px;
}

.group-and-btn-container {
  grid-area: group;
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  justify-content: space-between;
}

.list {
  display: flex;
  flex-direction: column;
  width: 300px;
  margin-right: 10px;
  vertical-align: top;
  background: #7cdedf;
  border-radius: 10px 10px;
  justify-content: center;
}

.group {
  display: flex;
  flex-direction: column;
  width: 300px;
  margin-right: 10px;
  vertical-align: top;
  background: #7cdedf;
  border-radius: 10px 10px;
  justify-content: center;
}

#group-header {
  grid-area: group-header;
}

#group {
  grid-area: group;
}
#group-card:hover{
  cursor: pointer;
  border: #7cdedf dotted;
  background-color: #fff7c6;

  font-size: 16px;
}

#addList {
  border-radius: 10px;
  cursor: pointer;
  height: 90px;
  width: 125px;
  border: solid #7cdedf;
  display: inline-block;
  background-color: #7cdedf;
  font-size: 16px;
  font-family: "Happy Monkey", cursive;
  font-weight: bold;
}

#addList:hover {
  cursor: pointer;
  height: 90px;
  width: 125px;
  border: #7cdedf dotted;
  display: inline-block;
  background-color: #fff7c6;

  font-size: 16px;
}

.create-join-btn-container.button {
  display: block;
}

#addGroup {
  display: block;
  margin-bottom: 5px;
  border-radius: 10px;
  cursor: pointer;
  height: 50px;
  width: 125px;
  border: solid #7cdedf;
  /* display: inline-block; */
  background-color: #7cdedf;
  font-size: 16px;
  font-family: "Happy Monkey", cursive;
  font-weight: bold;
}
#addGroup:hover {
  cursor: pointer;
  height: 50px;
  width: 125px;
  border: #7cdedf dotted;
  background-color: #fff7c6;
  font-size: 16px;
}
#joinGroup {
  border-radius: 10px;
  cursor: pointer;
  height: 50px;
  width: 125px;
  border: solid #7cdedf;
  display: inline-block;
  background-color: #7cdedf;
  font-size: 16px;
  font-family: "Happy Monkey", cursive;
  font-weight: bold;
}
#joinGroup:hover {
  cursor: pointer;
  height: 50px;
  width: 125px;
  border: #7cdedf dotted;
  /* display: inline-block; */
  background-color: #fff7c6;
  font-size: 16px;
  
}

#home-body {
  width: 100vw;
  height: 100vh;
  background-image: linear-gradient(to bottom, white, white, #fff7c6);
  overflow: scroll;
  margin-top: 80px;
  background-image:
            linear-gradient(to bottom,
              rgba(255,255,255, 0.2),
              rgba(255,247,198, 0.5)), url('../../images/background6.png');
  /* background-image: linear-gradient(to bottom, #ebfffd, #ebfffd, #b7f7f7), url('../../images/background2.png'); */
  background-repeat: no-repeat;
  background-size: cover;
  margin-left: 0px;
}

h1 {
  background-image: linear-gradient(to left, #ebfffd, #b7f7f7);
  width: 100%;
  text-align: center;
  border-radius: 10px 10px;
}
</style>
