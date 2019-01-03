package com.endymion.collection.test.ui.activity.contact;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.endymion.collection.R;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        init();
    }

    private void init() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // ContextCompat.checkSelfPermission() 方法 指定context和某个权限 返回PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED
            if (ContextCompat.checkSelfPermission(ContactActivity.this, android.Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                // 若不为GRANTED(即为DENIED)则要申请权限了
                // 申请权限 第一个为context 第二个可以指定多个请求的权限 第三个参数为请求码
                ActivityCompat.requestPermissions(ContactActivity.this,
                        new String[]{android.Manifest.permission.READ_CONTACTS},
                        101);
            } else {
                // 权限已经被授予，在这里直接写要执行的相应方法即可
                intentToContact();
            }
        } else {
            // 低于6.0的手机直接访问
            intentToContact();
        }
    }

    private void intentToContact() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri
        // 查询的字段
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1,
                "sort_key",
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY};
        // 按照sort_key升序查詢
        // 异步查询数据库类对象
        AsyncQueryHandler asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());
        asyncQueryHandler.startQuery(0, null, uri, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");
    }

    private static class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                List<Integer> contactIdList = new ArrayList<>();
                List<ContactBean> list = new ArrayList<>();
                cursor.moveToFirst(); // 游标移动到第一项
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);
                    String sortKey = cursor.getString(3);
                    int contactId = cursor.getInt(4);
                    Long photoId = cursor.getLong(5);
                    String lookUpKey = cursor.getString(6);

                    if (contactIdList.contains(contactId)) {
                        // 无操作
                        Log.w("Contact", "相同的contactId");
                    } else {
                        // 创建联系人对象
                        ContactBean contact = new ContactBean();
                        contact.setDisplayName(name);
                        contact.setPhoneNum(number);
                        contact.setSortKey(sortKey);
                        contact.setPhotoId(photoId);
                        contact.setLookUpKey(lookUpKey);
                        list.add(contact);

                        contactIdList.add(contactId);
                    }
                }
                if (list.size() > 0) {
//                    setAdapter(list);
//                    Toast.makeText(ContactActivity.this, "list不为空", Toast.LENGTH_LONG).show();
                    Log.w("Contact", "list = " + list.toString());
                }
            }
            super.onQueryComplete(token, cookie, cursor);
        }
    }

}
