<template>
  <div>
    <div v-for="(link,index) in linkList" :key="index">
      <a @click="download(link.fileAddress,link.fileName)" v-text="link.fileName" target="_blank"/>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FileAbout',
  data () {
    return {
      linkList: []
    }
  },
  mounted () {
    this.$axios.get('file/fileList', {params: {id: 'xxx'}}).then(res => {
      this.linkList = res.data.data
    })
  },
  methods: {
    download (path, fileName) {
      window.open('file/fileInfos?path=' + encodeURI(path) + '&fileName=' + encodeURI(fileName), '_blank')
    }
  }
}
</script>

<style scoped>

</style>
