<template>
  <div>
    <div id="test-editormd-view">
      <textarea style="display: none;" name="test-editormd-markdown-doc">###Hello world!</textarea>
    </div>
    <div id="test-editormd-view2">
      <textarea style="display: none;" id="append-test" >###Hello world2!</textarea>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MdToHtml',
  data () {
    return {}
  },
  mounted () {
    // eslint-disable-next-line no-unused-vars
    var testEditormdView, testEditormdView2
    // eslint-disable-next-line no-undef
    testEditormdView = this.$editormd.markdownToHTML('test-editormd-view', {
      // eslint-disable-next-line no-undef
      markdown: markdown,
      htmlDecode: 'style,script,iframe',
      tocm: true,
      emoji: true,
      taskList: true,
      tex: true,
      flowChart: true,
      sequenceDiagram: true
    })
    // eslint-disable-next-line no-undef
    testEditormdView2 = this.$editormd.markdownToHTML('test-editormd-view2', {
      htmlDecode: 'style,script,iframe',
      emoji: true,
      taskList: true,
      tex: true,
      flowChart: true,
      sequenceDiagram: true
    })
  },
  methods: {
    markdownToHtml () {
      // eslint-disable-next-line no-unused-vars
      var testEditormdView
      // eslint-disable-next-line no-undef
      testEditormdView = this.$editormd.markdownToHTML('test-editormd-view', {
        // eslint-disable-next-line no-undef
        markdown: markdown,
        htmlDecode: 'style,script,iframe',
        tocm: true,
        emoji: true,
        taskList: true,
        tex: true,
        flowChart: true,
        sequenceDiagram: true
      })
    }
  }
}
</script>
<!--<script type="text/javascript">
$(function () {
  var testEditormdView, testEditormdView2

  $.get('test.md', function (markdown) {

    testEditormdView = editormd.markdownToHTML('test-editormd-view', {
      markdown: markdown,//+ "\r\n" + $("#append-test").text(),
      //htmlDecode      : true,       // 开启 HTML 标签解析，为了安全性，默认不开启
      htmlDecode: 'style,script,iframe',  // you can filter tags decode
      //toc             : false,
      tocm: true,    // Using [TOCM]
      //tocContainer    : "#custom-toc-container", // 自定义 ToC 容器层
      //gfm             : false,
      //tocDropdown     : true,
      // markdownSourceCode : true, // 是否保留 Markdown 源码，即是否删除保存源码的 Textarea 标签
      emoji: true,
      taskList: true,
      tex: true,  // 默认不解析
      flowChart: true,  // 默认不解析
      sequenceDiagram: true,  // 默认不解析
    })

    //console.log("返回一个 jQuery 实例 =>", testEditormdView);

    // 获取Markdown源码
    //console.log(testEditormdView.getMarkdown());

    //alert(testEditormdView.getMarkdown());
  })

  // testEditormdView2 = editormd.markdownToHTML("test-editormd-view2", {
  //   htmlDecode      : "style,script,iframe",  // you can filter tags decode
  //   emoji           : true,
  //   taskList        : true,
  //   tex             : true,  // 默认不解析
  //   flowChart       : true,  // 默认不解析
  //   sequenceDiagram : true,  // 默认不解析
  // });
})
</script>-->
<style scoped>

</style>
