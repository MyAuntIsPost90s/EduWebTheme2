import Axios from 'axios';
import Config from './config';
import {Message, Loading} from 'element-ui';

//网关
let Gateway = {
  get(params) {
    return new Promise(function (resolve, reject) {
      //loading
      let loadingHandler = Loading.service({
        lock: true,
        text: 'loading...'
      });
      //请求数据
      Axios.get(Config.URL_HEAD + params.url, {
        headers: getHeaders(),
        params: params.data
      }).then(function (resp) {
        loadingHandler.close();
        if (resp.code != 1) {
          Message.error(resp.msg);
          reject(resp.msg);
          return;
        }
        resolve(resp);
      });
    });
  },
  post(params) {
    return new Promise(function (resolve, reject) {
      //loading
      let loadingHandler = Loading.service({
        lock: true,
        text: 'loading...'
      });
      //请求数据
      Axios.post(Config.URL_HEAD + params.url
        , getParams(params.data)
        , {
          headers: getHeaders(),
        }).then(function (resp) {
        loadingHandler.close();
        if (resp.code != 1) {
          Message.error(resp.msg);
          reject(resp.msg);
          return;
        }
        resolve(resp);
      })
    });
  },
  setToken(token) {
    window.localStorage.setItem(Config.TOKEN_KEY, token);
  },
  getToken() {
    return window.localStorage.getItem(Config.TOKEN_KEY);
  },
  download: function (url, param) {
    var $iframe = document.createElement('iframe');
    $iframe.setAttribute('id','downloadIframe');
    $iframe.setAttribute('src',url);
    var $form = document.createElement('form')
    $form.setAttribute('target','downloadIframe');
    $form.setAttribute('method','post');
    $form.setAttribute('action',url);
    if (param != null) {
      for (var key in param) {
        $form.innerHTML += '<textarea type="hidden" name="' + key + '">' + param[key] + '</textarea>';
      }
    }
    $iframe.appendChild($form);
    document.body.appendChild($iframe);
    $form.submit();
    $iframe.remove();
  }
}

//字段校验
let FormValid = {
  check(param) {
    if (param && param.valid) {
      for (let field in param.valid) {
        let val = param[field];
        let validStr = param.valid[field];
        let valid = eval('({' + validStr + '})');
        if (!valid) {
          continue;
        }
        if (!valid.name) {
          valid.name = field;
        }
        if (valid.notEmpty === true) { //判断非空
          if (!val) {
            param.e = valid.name + '不能为空';
            return false;
          }
        }
        if (valid.length) { //判断长度
          if (!val) {
            param.e = valid.name + '长度应在' + valid.length[0] + '到' + valid.length[1] + '之间';
            return false;
          }
          if (val.length < valid.length[0] || val.length > valid.length[1]) {
            param.e = valid.name + '长度应在' + valid.length[0] + '到' + valid.length[1] + '之间';
            return false;
          }
        }
      }
      return true;
    }
    return true;
  }
}

function getParams(data) {
  if (data == null) {
    return null;
  }
  var params = new URLSearchParams();
  for (let field in data) {
    if (field !== 'valid') {
      if (data[field]) {
        params.append(field, data[field]);
      }
    }
  }
  return params;
}

function getHeaders() {
  return {
    'AccessToken': Gateway.getToken(),
    'AppKey': Config.APP_KEY
  }
}

export default {
  Gateway: Gateway,
  FormValid: FormValid,
}
