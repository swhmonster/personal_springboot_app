<template>
  <div>
    <div v-for="(link,index) in linkList" :key="index">
      <a :href="link.fileAddress" v-text="link.fileName" target="_blank"/>
    </div>
  </div>
</template>
<script src="./index.js"></script>
<style scoped>

</style>
