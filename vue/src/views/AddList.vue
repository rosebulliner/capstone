<template>
  <div>
      <h1>Add New List</h1>
      <create-list />
  </div>
</template>

<script>
import CreateList from "../components/CreateList.vue"

export default {
    name: "add-list",
  components: { CreateList }

}
</script>

<style>

</style>