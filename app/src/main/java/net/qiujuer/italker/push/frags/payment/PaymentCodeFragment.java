package net.qiujuer.italker.push.frags.payment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.ResultPoint;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.push.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentCodeFragment extends Fragment {
    private static final String TAG = PaymentCodeFragment.class.getSimpleName();
    private BeepManager beepManager;
    private String lastText;
    private String toast;

    private void displayToast() {
        if (getActivity() != null && toast != null) {
            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
            toast = null;
        }
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        barcodeView.decodeContinuous(callback);
        beepManager = new BeepManager(getActivity());
        changeToPay();
    }

    public PaymentCodeFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.iv_code)
    ImageView mCode;

    @BindView(R.id.barcode_scanner)
    DecoratedBarcodeView barcodeView;

    @OnClick(R.id.scan)
    void onScanClick(View view) {
        changeToScan();

    }

    void changeToPay() {
        mCode.setVisibility(View.VISIBLE);
        barcodeView.setVisibility(View.GONE);
        onPayClick();
        barcodeView.pause();
    }

    void changeToScan() {
        mCode.setVisibility(View.GONE);
        barcodeView.setVisibility(View.VISIBLE);
        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }

            lastText = result.getText();
            barcodeView.setStatusText(result.getText());
            beepManager.playBeepSoundAndVibrate();

        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @OnClick(R.id.pay)
    void onPayClick(View view) {
        changeToPay();
    }

    void onPayClick() {
        Bitmap qrBitmap = generateBitmap("http://www.csdn.net", 400, 400);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap bitmap = addLogo(qrBitmap, logoBitmap);
        mCode.setImageBitmap(bitmap);
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_payment_code;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event);
    }

    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                toast = "Cancelled from fragment";
            } else {
                toast = "Scanned from fragment: " + result.getContents();
            }

            // At this point we may or may not have a reference to the activity
            displayToast();
        }
    }
}
