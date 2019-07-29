import {getRequest} from './api'
import {Message} from 'element-ui'

export const isNotNullORBlank = (...args)=> {
  for (var i = 0; i < args.length; i++) {
    var argument = args[i];
    if (argument == null || argument == '' || argument == undefined) {
      Message.warning({message: '数据不能为空!'})
      return false;
    }
  }
  return true;
}

export const initMenu = (router, store)=> {
  // alert(store.state.routes.length);
  if (store.state.routes.length > 0) {
    return;
  }
  getRequest("/config/sysmenu").then(resp=> {
    if (resp && resp.status == 200) {
      // alert('resp:'+JSON.stringify(resp));
      var fmtRoutes = formatRoutes(resp.data);
      
      router.addRoutes(fmtRoutes);
      store.commit('initMenu', fmtRoutes);
      // store.dispatch('connect');
    }
  })
}

export const formatRoutes = (routes)=> {
  let fmRoutes = [];
  routes.forEach(router=> {
    let {
      path,
      component,
      name,
      children,
      meta
    } = router;
    if (children && children instanceof Array) {
      children = formatRoutes(children);
    }
    let fmRouter = {
      path: path,
      component(resolve){
        if (component.startsWith("Home")) {
          require(['../components/' + component + '.vue'], resolve)
        } else if (component.startsWith("Adm")) {
          require(['../components/adm/' + component + '.vue'], resolve)
        } else if (component.startsWith("Off")) {
          require(['../components/off/' + component + '.vue'], resolve)
        } else if (component.startsWith("Tea")) {
          require(['../components/tea/' + component + '.vue'], resolve)
        } else if (component.startsWith("Stu")) {
          require(['../components/stu/' + component + '.vue'], resolve)
        }
      },
      name: name,
      children: children,
      meta: meta
    };
    
    fmRoutes.push(fmRouter);
  })
  return fmRoutes;
}
