package com.loveplusplus.update.web;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.loveplusplus.update.MLogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public abstract class BaseAjaxCallBack implements AjaxCallBack {
	@SuppressWarnings("unchecked")
	public void callBack(ResponseEntity status) {
		MLogUtil.e("返回数据：  " + status.toString());
//		DialogUtil.dismissLoadingDialog();
		if (status.getStatus() == -1) {
			onFailure(-404, "网络连接失败，请检查网络");
			return;
		}
		
		getResponse(status);

		try {
			JSONObject resultJSON = new JSONObject(status.getContentAsString());
			if(!resultJSON.has("code")){
				onSuccess(new Res_BaseBean(resultJSON));
			}else {
				int statusCode = Integer.valueOf(resultJSON.get("code").toString());
				String message = resultJSON.get("error").toString();
				onFailure(statusCode, message);
			}
//			int statusCode = Integer.valueOf(resultJSON.get("code").toString());
//			if (statusCode == null) {
//				onSuccess(new Res_BaseBean(resultJSON));
//			} else {
//				// 自动登录失效，重新登录
//				if(statusCode == 2 ){
////					MyApplication.getApplication().setUser(null);
//				}
//				String message = resultJSON.get("message").toString();
//				onFailure(statusCode, message);
//			}

		} catch (Exception e) {
			MLogUtil.e("Exception " + e.toString());
			onFailure(-999, "接收数据异常");
		}
	}
	
	protected void getResponse(ResponseEntity response) {
	}

	public static class Res_BaseBean {
		private JSONObject datas;

		public Res_BaseBean(JSONObject data) {
			this.datas = data;
		}

		public JSONObject getData(){
			return datas;
		}

		public String getDataCode(String param){
			try {
				return datas.get(param).toString();
			} catch (JSONException e) {
				e.printStackTrace();
				return "";
			}
		}

//		public String getData(){
//			try {
//				return datas.toString();
//			} catch (Exception e) {
//				return "";
//			}
//		}
		
		public <T> List<T> getDataList(Class<T> cls) {
			if (!datas.isNull("data")) {
				try {
					return JSON.parseArray(datas.get("data").toString(), cls);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			return null;
		}

		public <T> List<T> getJsonList(Class<T> cls,String json) {
				try {
					return JSON.parseArray(json, cls);
				} catch (Exception e) {
					e.printStackTrace();
				}
			return null;
		}
		
		public <T> List<T> getDataList(Class<T> cls, String param) {
			if (TextUtils.isEmpty(param)) {
				param = "data";
			}

			if (!datas.isNull(param)) {
				try {
					return JSON.parseArray(datas.get(param).toString(), cls);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			return null;
		}

		public <T> T getData(Class<T> cls, String param) {
			if (TextUtils.isEmpty(param)) {
				param = "data";
			}

			if (!datas.isNull(param)) {
				try {
					return JSON.parseObject(datas.get(param).toString(), cls);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			return null;
		}
		
		public <T> T getData(Class<T> cls) {
			if (!datas.isNull("data")) {
				try {
					return JSON.parseObject(datas.get("data").toString(), cls);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		public Res_BaseBean() {
		}
	}

	public boolean stop() {
		return false;
	}

	public abstract void onSuccess(Res_BaseBean t);

	public abstract void onFailure(int status, String msg);
}
