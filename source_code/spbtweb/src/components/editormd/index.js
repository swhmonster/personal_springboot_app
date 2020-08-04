export default {
  name: 'MdToHtml',
  data () {
    return {}
  },
  mounted () {
    // eslint-disable-next-line no-unused-vars
    var testEditormdView, testEditormdView2
    var _this = this
    this.$axios.get('markdown/mdfile', {params: {id: 'xxx'}}).then(res => {
      testEditormdView = editormd.markdownToHTML('test-editormd-view', {
        // eslint-disable-next-line no-undef
        markdown: res,
        htmlDecode: 'style,script,iframe',
        tocm: true,
        emoji: true,
        taskList: true,
        tex: true,
        flowChart: true,
        sequenceDiagram: true
      })
      // eslint-disable-next-line no-undef
      testEditormdView2 = editormd.markdownToHTML('test-editormd-view2', {
        htmlDecode: 'style,script,iframe',
        emoji: true,
        taskList: true,
        tex: true,
        flowChart: true,
        sequenceDiagram: true
      })
    })
  },
  methods: {
    markdownToHtml () {
      // eslint-disable-next-line no-unused-vars
      var testEditormdView, testEditormdView2
      var _this = this
      this.$axios.get('markdown/mdfile', {params: {id: 'xxx'}}).then(res => {
        testEditormdView = _this.$editormd.markdownToHTML('test-editormd-view', {
          // eslint-disable-next-line no-undef
          markdown: res,
          htmlDecode: 'style,script,iframe',
          tocm: true,
          emoji: true,
          taskList: true,
          tex: true,
          flowChart: true,
          sequenceDiagram: true
        })
        // eslint-disable-next-line no-undef
        testEditormdView2 = _this.$editormd.markdownToHTML('test-editormd-view2', {
          htmlDecode: 'style,script,iframe',
          emoji: true,
          taskList: true,
          tex: true,
          flowChart: true,
          sequenceDiagram: true
        })
      })
    }
  }
}
