<template>
  <div class="hello" >
    <h1>{{ msg }}</h1>
    <h2>{{ msg2 }}</h2>
    <h3>{{ msg3 }}</h3>
    <h4>{{ msg4 }}</h4>
    <h5>{{ msg5 }}</h5>
    <!--<div style="width:300px;margin:0 auto; padding:20px 0;">
      <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=32010602010775"
         style="display:inline-block;text-decoration:none;height:20px;line-height:20px;">
        <img src="../assets/beian.png" style="float:left;"/>
        <p style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px; color:#939393;">苏公网安备
          32010602010775号</p></a>
    </div>-->
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Walter Sun',
      msg2: 'Welcome, the new world is building...',
      msg3: 'My blog:https://blog.csdn.net/Soul_Programmer_Swh',
      msg4: 'My github:https://github.com/swhmonster',
      msg5: 'My email:swh7941@qq.com'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1,h2 {
    font-weight: bolder;
    font-size: xx-large;
  }
  h3,h4,h5{
    font-weight: lighter;
    font-size: small;
  }
</style>
