<template>
  <div  id="group">
      <div class="group-container">
<h2  class="group-title">{{ group.name }}</h2>
       <font-awesome-icon v-on:click="openGroupShareModal()" class="share-group" :icon="['fas', 'user-plus']" />
      </div>

      <!-- group share popup -->
      <vue-modal-2 class="modal" :headerOptions="{
        title: 'To share copy the link below',
      }" 
      lightBg= '#EBFFFD'
      :footerOptions="{
        
        btn1: 'Copy, completed',
        disableBtn2: true,
        btn1Style: {
          backgroundColor: '#FFF7C6',
        } ,
        btn2OnClick: () => {
          editItem();
          
        },
        btn1OnClick: () => {
          $vm2.close('group-share-modal-' + this.group.name);
        },
      }" :name="'group-share-modal-' + this.group.name" @on-close="closeGroupShareModal()">
        Group share code: [insert code here]
        </vue-modal-2>
  </div>
</template>

<script>
export default {
    name: "group",

    props:["group"],
    data(){
        return {
            name: ""
        }
    },
    methods: {
         openGroupShareModal () {
    this.$vm2.open('group-share-modal-' + this.group.name)
    },
    closeGroupShareModal () {
    this.$vm2.close('group-share-modal-' + this.group.name)
    },
    }

}
</script>

<style>

.group-container{
    display: flex;
    justify-content: space-between;
}


.group-title{
    font-size: 30px;
}

.share-group{
align-self: center;
margin-right: 10px;
cursor: pointer;
font-size: 40px;
}
</style>