package com.br.apposcar;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;

public class Download  extends AsyncTask<String, Void, Bitmap> {
    private Context ctx;
    private ImageView image;
    private ProgressDialog progressDialog;

    public Download(Context ctx, Object image){
        this.ctx=ctx;
        this.image= (ImageView) image;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = (ProgressDialog) ProgressDialog.show(ctx, "Porfavor aguarde....", "Baixando a sua imagem");
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap imagemBitmap = null;

        try{
            imagemBitmap = Auxiliar.baixarImagem(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagemBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
        progressDialog.dismiss();
    }
}
