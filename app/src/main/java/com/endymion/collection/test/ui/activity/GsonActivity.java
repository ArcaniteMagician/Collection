package com.endymion.collection.test.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.endymion.collection.R;
import com.endymion.collection.test.model.Occupation;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GsonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);

        Foo<Occupation> foo = new Foo<Occupation>() {
        };
        // 在类的外部这样获取
        Type type = ((ParameterizedType) foo.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println(type);
        // 在类的内部这样获取
        System.out.println(foo.getTClass());

        String jsonString = "{\n" +
                "\"name\":\"lian\",\n" +
                "\"id\":\"9527\"\n" +
                "}";
        Occupation jsonRootBean = new Gson().fromJson(jsonString, type);
        System.out.println(jsonRootBean.toString());
    }

    abstract class Foo<T> {
        public Class<T> getTClass() {
            Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return tClass;
        }
    }
}
