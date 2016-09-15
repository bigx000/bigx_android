/**
 * com.taozhenli.app.component
 * AsyncImageTool.java
 * 2015年7月6日 上午11:41:20
 * @author: z```s
 */
package com.bigx.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bigx.config.BigxConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p></p>
 * 2015年7月6日 Administrator
 * @author: z```s
 */
public enum AsyncImageTool {

	INSTANCE;
	
	private String TAG = AsyncImageTool.class.getSimpleName();
	
	private ExecutorService executor;
	
	private BitmapFactory.Options defaultOptions;
	
	private String cacheDir;
	
	/**
	 * Constructor Method
	 * 2015年7月6日 上午11:59:45
	 * @author: z```s
	 */
	AsyncImageTool() {
		executor = Executors.newFixedThreadPool(10);
		defaultOptions = new BitmapFactory.Options();
		defaultOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		defaultOptions.inScaled = false;
		defaultOptions.inPurgeable = true;
		defaultOptions.inInputShareable = true;
		cacheDir = BigxConstant.CACHE_PICTURE_PATH;
		Log.i(TAG, "cache path : " + cacheDir);
		File root = new File(cacheDir);
		if (root.exists()) {
			File[] fs = root.listFiles();
			if (fs != null) {
				for (File f : fs) {
					if (f.isFile()) {
						f.delete();
					}
				}
			}
		} else {
			root.mkdirs();
		}
		
	}
	
	public void stop() {
		if (!executor.isTerminated()) {
			executor.shutdownNow();
		}
	}
	
	public void load(ImageView imageView, String url) {
		this.load(imageView, null, url, true, false, null, null, null);
	}
	
	public void load(ImageView imageView, String url, boolean cached, boolean permanent) {
		this.load(imageView, null, url, cached, permanent, null, null, null);
	}
	
	/**
	 * <p>加载图片</p>
	 * @param imageView
	 * @param options
	 * @param url
	 * @param cached 是否缓存
	 * @param permanent 是否永久缓存
	 * @param progressBar 进度条
	 * @param listener 状态监听器
	 * @param handler
	 * 2015年7月6日 下午1:16:40
	 * @author: z```s
	 */
	public void load(final ImageView imageView, final BitmapFactory.Options options, final String url,
					 final boolean cached, final boolean permanent, final ProgressBar progressBar,
					 final LoadStateChangedListener listener, Handler handler) {
		final Handler h = handler != null ? handler : new Handler(Looper.getMainLooper());
		executor.execute(new Runnable() {
			@Override
			public void run() {
				Object tag = imageView.getTag();
				if (tag == null || tag instanceof LoadState) {
					imageView.setTag(new LoadState(url, listener));
				}
				LoadState ls = (LoadState) imageView.getTag();
				if (ls.getState() == LoadState.STATE_LOADED && ls.getUrl().equals(url)
						&& cached) {
					ls.setState(LoadState.STATE_LOADED);
				}
				if (progressBar != null) {
					h.post(new Runnable() {
						
						@Override
						public void run() {
							progressBar.setVisibility(View.VISIBLE);
						}
					});
				}
				Bitmap bitmap = null;
				if (cached) {
					if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
						FileInputStream fis = null;
						try {
							File f = new File(cacheDir + (permanent ? "/permanent" : "/tmp") + "/" + digest(url));
							if (f.exists()) {
								Log.i(TAG, "load from cache: " + url);
								fis = new FileInputStream(f);
								bitmap = BitmapFactory.decodeStream(fis, null, options == null ? defaultOptions : options);
								fis.close();
								fis = null;
							}
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (fis != null) {
								try {
									fis.close();
									fis = null;
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				if (bitmap == null) {
					try {
						Log.i(TAG, "load from netword: " + url);
						bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent(), null, options == null ? defaultOptions : options);
						if (bitmap != null && cached
								&& Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
							File dir = new File(cacheDir + (permanent ? "/permanent" : "/tmp"));
							if (!dir.exists()) {
								dir.mkdirs();
							}
							File bitmapFile = new File(cacheDir + (permanent ? "/permanent" : "/tmp") + "/" + digest(url));
							if (!bitmapFile.exists()) {
								bitmapFile.createNewFile();
							}
							FileOutputStream fos = new FileOutputStream(bitmapFile);
							bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
							fos.flush();
							fos.close();
							fos = null;
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
				}
				if (bitmap != null) {
					final Bitmap bm = bitmap;
					h.post(new Runnable() {
						
						@Override
						public void run() {
							imageView.setImageBitmap(bm);
						}
					});
					ls.setState(LoadState.STATE_LOADED);
				} else {
					ls.setState(LoadState.STATE_ERROR);
				}
				if (progressBar != null) {
					h.post(new Runnable() {
						
						@Override
						public void run() {
							progressBar.setVisibility(View.GONE);
						}
					});
				}
			}
		});
	}
	
	class LoadState {
		
		public static final int STATE_LOADING = 0;
		public static final int STATE_LOADED = 1;
		public static final int STATE_ERROR = -1;
		public static final int STATE_IDLE = -2;
		
		private String url;
		private int state;
		LoadStateChangedListener listener;
		
		/**
		 * Constructor Method
		 * 2015年7月6日 下午12:49:28
		 * @author: z```s
		 */
		public LoadState(String url, LoadStateChangedListener listener) {
			this.url = url;
			this.listener = listener;
			this.state = STATE_IDLE;
		}
		
		/**
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}
		
		/**
		 * @param url the url to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}
		
		/**
		 * @return the state
		 */
		public int getState() {
			return state;
		}
		
		/**
		 * @param state the state to set
		 */
		public void setState(int state) {
			this.state = state;
			if (listener != null) {
				listener.onLoadStateChanged(state);
			}
		}
		
	}
	
	public static interface LoadStateChangedListener {
		
		// 加载状态改变事件
		public void onLoadStateChanged(int state);
		
	}
	
	/**
	 * <p></p>
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * 2015年7月6日 下午12:56:20
	 * @author: z```s
	 */
	public String digest(String input) throws NoSuchAlgorithmException {
		String value = null;
		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(input.getBytes());
		byte[] tmp = md.digest();
		char[] _value = new char[32];
		int index = 0;
		for (int i = 0; i < 16; i++) {
			byte byte0 = tmp[i];
			_value[index++] = hexDigits[byte0 >>> 4 & 0xf];
			_value[index++] = hexDigits[byte0 & 0xf];
		}
		value = new String(_value).toLowerCase(Locale.ENGLISH);
		return value;
	}
	
}
