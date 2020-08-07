<template>
  <div class="container">
    <!-- Static navbar -->
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                  aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Walter Sun</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#/md" target="_blank">Documents</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                 aria-expanded="false">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li class="active"><a href="./">Default <span class="sr-only">(current)</span></a></li>
            <li><a href="../navbar-static-top/">Static top</a></li>
            <li><a href="../navbar-fixed-top/">Fixed top</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div><!--/.container-fluid -->
    </nav>
    <!-- Main component for a primary marketing message or call to action -->
    <div class="jumbotron">
      <h1>Walter Sun</h1>
      <h2>Welcome, the new world is building...</h2>
      <p>My blog: https://blog.csdn.net/Soul_Programmer_Swh</p>
      <p>My github: https://github.com/swhmonster</p>
      <p>My email: swh7941@qq.com</p>
      <p>
        <a class="btn btn-lg btn-primary" href="../../components/#navbar" role="button">Learn more &raquo;</a>
      </p>
    </div>
  </div> <!-- /container -->
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {}
  }
}
</script>

<style scoped>
</style>
