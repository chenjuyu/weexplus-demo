/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.farwolf.qr.zxing.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.farwolf.libary.R;
import com.farwolf.qr.view.QRView;
import com.farwolf.qr.zxing.camera.CameraManager;
import com.farwolf.qr.zxing.decode.DecodeThread;
import com.farwolf.qr.zxing.view.ViewfinderResultPointCallback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;

import java.util.Collection;
import java.util.Map;

/**
 * This class handles all the messaging which comprises the state machine for
 * capture. 该类用于处理有关拍摄状态的所有信息
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class CaptureActivityHandler extends Handler {

	private static final String TAG = CaptureActivityHandler.class
			.getSimpleName();

	private final  QRView qrView;
	private final DecodeThread decodeThread;
	private State state;
	private final CameraManager cameraManager;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(QRView qrView,
			Collection<BarcodeFormat> decodeFormats,
			Map<DecodeHintType, ?> baseHints, String characterSet,
			CameraManager cameraManager) {
		this.qrView = qrView;
		decodeThread = new DecodeThread(qrView, decodeFormats, baseHints,
				characterSet, new ViewfinderResultPointCallback(
				qrView.getViewfinderView()));
		decodeThread.start();
		state = State.SUCCESS;

		// Start ourselves capturing previews and decoding.
		// 开始拍摄预览和解码
		this.cameraManager = cameraManager;
		cameraManager.startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		int v=message.what;

		if (v==R.id.restart_preview) {
			// 重新预览
			restartPreviewAndDecode();
		}
		else if (v== R.id.decode_succeeded) {
			// 解码成功
			state = State.SUCCESS;
			Bundle bundle = message.getData();
			Bitmap barcode = null;
			float scaleFactor = 1.0f;
			if (bundle != null) {
				byte[] compressedBitmap = bundle
						.getByteArray(DecodeThread.BARCODE_BITMAP);
				if (compressedBitmap != null) {
					barcode = BitmapFactory.decodeByteArray(compressedBitmap,
							0, compressedBitmap.length, null);
					// Mutable copy:
					barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
				}
				scaleFactor = bundle
						.getFloat(DecodeThread.BARCODE_SCALED_FACTOR);
			}
			qrView.handleDecode((Result) message.obj, barcode, scaleFactor);


			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					state = State.PREVIEW;
					cameraManager.requestPreviewFrame(decodeThread.getHandler(),
							R.id.decode);
				}
			}, 1500);

		}
		else if(v== R.id.decode_failed) {

			// We're decoding as fast as possible, so when one decode fails,
			// start another.
			// 尽可能快的解码，以便可以在解码失败时，开始另一次解码
			state = State.PREVIEW;
			cameraManager.requestPreviewFrame(decodeThread.getHandler(),
					R.id.decode);

		}
		else if(v== R.id.return_scan_result) {
			//扫描结果，返回CaptureActivity处理
//			qrView.setResult(Activity.RESULT_OK, (Intent) message.obj);
//			qrView.finish();
		}

	}

	/**
	 * 完全退出
	 */
	public void quitSynchronously() {
		state = State.DONE;
		cameraManager.stopPreview();
		Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
		quit.sendToTarget();
		try {
			// Wait at most half a second; should be enough time, and onPause()
			// will timeout quickly
			decodeThread.join(500L);
		} catch (InterruptedException e) {
			// continue
		}

		// Be absolutely sure we don't send any queued up messages
		//确保不会发送任何队列消息
		removeMessages(R.id.decode_succeeded);
		removeMessages(R.id.decode_failed);
	}

	public void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			cameraManager.requestPreviewFrame(decodeThread.getHandler(),
					R.id.decode);
			qrView.drawViewfinder();
		}
	}

}
