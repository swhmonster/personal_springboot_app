export default {
  name: 'MdToHtml',
  data () {
    return {
      linkList: []
    }
  },
  mounted () {
    this.$axios.get('markdown/mdfile', {params: {id: 'xxx'}}).then(res => {
      this.linkList = res.data.data
    })
  },
  methods: {}
}
