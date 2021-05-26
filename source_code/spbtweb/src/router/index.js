import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import MdToHtml from '../components/editormd/MdToHtml'
import FileAbout from '../components/FileAbout'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/md',
      name: 'mdToHtml',
      component: MdToHtml
    },
    {
      path: '/fileDownload',
      name: 'FileAbout',
      component: FileAbout
    }
  ]
})
