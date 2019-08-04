package com.example.supervizor.Fragment.Company;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.R;
import com.kinda.alert.KAlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class User_Attendance_F extends Fragment {
    ScrollView linearLayout_attendance;
    Bitmap bitmap;
    Spinner month_spinner;
    Spinner year_spinner;
    TextView name_TV;
    TextView designation_TV;
    TableLayout tableLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.GONE);

        return inflater.inflate(R.layout.user_attendance_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//for option menu
        setHasOptionsMenu(true);
        tableLayout = view.findViewById(R.id.table);
        linearLayout_attendance = view.findViewById(R.id.attendance_view_layout);


        month_spinner = view.findViewById(R.id.month_spinner_ID_user__attendance_company);
        year_spinner = view.findViewById(R.id.year_spinner_ID_user__attendance_company);
        name_TV = view.findViewById(R.id.name_attendance_company);
        designation_TV = view.findViewById(R.id.designation_attendance_company);


        month_spinner.getBackground().setColorFilter(getResources().getColor(R.color.text_white_color), PorterDuff.Mode.SRC_ATOP);
        year_spinner.getBackground().setColorFilter(getResources().getColor(R.color.text_white_color), PorterDuff.Mode.SRC_ATOP);

        String[] month_array = {"January", "February", "March", "April", "May", "june", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapter_month =
                new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, month_array);
        adapter_month.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(adapter_month);

        String[] year_array = {"2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        ArrayAdapter<String> adapter_year =
                new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, year_array);
        adapter_year.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(adapter_year);

//SET current month
        Calendar cal = Calendar.getInstance();
        String current_month = month_array[cal.get(Calendar.MONTH)];
        int selectionPosition = adapter_month.getPosition(current_month);
        month_spinner.setSelection(selectionPosition);
//SET current month End

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

//        inflater.inflate(R.menu.company_main,menu);
        menu.findItem(R.id.pdf_generate_to_this_employee).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pdf_generate_to_this_employee:


                KAlertDialog kAlertDialog = new KAlertDialog(getContext());
                kAlertDialog.setTitleText("Alert !!");

                kAlertDialog.setContentText("Do You want to create pdf ?");
                kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {

                    Log.d("size", " " + tableLayout.getWidth() + "  " + tableLayout.getWidth());
                    bitmap = loadBitmapFromView(tableLayout, tableLayout.getWidth(), tableLayout.getHeight());
                    createPdf();
                    kAlertDialog1.dismiss();
                });
                kAlertDialog.setCancelClickListener(kAlertDialog12 -> kAlertDialog.dismiss());
                kAlertDialog.show();

                break;
        }
        return false;
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Attendance");
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight;
        int convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // create a File object for the parent directory
        File wallpaperDirectory = new File("/sdcard/OfficeManagement_APP/");
// have the object build the directory structure, if needed.
        wallpaperDirectory.mkdirs();

        // write the document content
        String targetPdf = "/sdcard/OfficeManagement_APP/" + designation_TV.getText().toString() + "_" + year_spinner.getSelectedItem().toString() + "_" + month_spinner.getSelectedItem().toString() + ".pdf";

        File filePath = new File(targetPdf);

        try {
            document.writeTo(new FileOutputStream(filePath));
            // close the document
            document.close();
            Toasty.success(getContext(), "PDF is created!!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), targetPdf, Toast.LENGTH_SHORT).show();

            openGeneratedPDF();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG - ", "createPdf: " + e.toString());
            Toasty.error(getContext(), "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }


    }

    private void openGeneratedPDF() {
        File file = new File("/sdcard/OfficeManagement_APP/" + designation_TV.getText().toString() + "_" + year_spinner.getSelectedItem().toString() + "_" + month_spinner.getSelectedItem().toString() + ".pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
}
