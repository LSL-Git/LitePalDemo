package lsl.com.dblitepaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button but_create_database;
    private Button but_add_data;
    private Button but_update_data;
    private Button but_del_data;
    private Button but_query_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but_create_database = (Button) findViewById(R.id.create_database);
        but_add_data = (Button) findViewById(R.id.add_data);
        but_update_data = (Button) findViewById(R.id.update_data);
        but_del_data = (Button) findViewById(R.id.delete_data);
        but_query_data = (Button) findViewById(R.id.query_data);

        but_create_database.setOnClickListener(this);
        but_add_data.setOnClickListener(this);
        but_update_data.setOnClickListener(this);
        but_del_data.setOnClickListener(this);
        but_query_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_database:
                Connector.getDatabase(); // 创建数据库
                break;
            case R.id.add_data: // 向数据库添加数据
                Book book = new Book();
                book.setName("The Da K Code");
                book.setAuthor("LSL");
                book.setPags(450);
                book.setPrice(25.5);
                book.setPress("Unknow");
                if (book.save()) {
                    Toast.makeText(this,"save success ",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update_data:
//                Book book1 = new Book();
//                book1.setName("The Da K Code");
//                book1.setAuthor("LSL");
//                book1.setPags(450);
//                book1.setPrice(25.5);
//                book1.setPress("Unknow");
//                book1.save();
//                book1.setPrice(25.6);
//                book1.save();
                Book book1 = new Book();
                book1.setPrice(38.4);// 需要更新的内容
                int result = book1.updateAll("name = ? and author = ?","The Da K Code","LSL"); // 更新条件 如果条件为空 则更新所有
                Toast.makeText(this,"updata row :" + result, Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_data:
                int result2 = DataSupport.deleteAll(Book.class,"pags < ?", "1"); // 删除条件
                Toast.makeText(this,"delete row :" + result2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.query_data:
//                List<Book> books = DataSupport.findAll(Book.class); // 查询全部
//                List<Book> books = DataSupport.select("name","author").find(Book.class);//查找相应字段
//                List<Book> books = DataSupport.select("name","author").where("name = ?","The Da K Code").find(Book.class); // 按条件查找 相应字段
//                List<Book> books = DataSupport.order("price desc").find(Book.class);//查找并排序 desc将序  asc 升序
//                double books = DataSupport.average(Book.class,"price"); // 求某字段的平均值
//                Log.e("MainActivity","book price average is:" + books);
                List<Book> books = DataSupport.limit(2).offset(1).find(Book.class); // 指定条数查询  指定查两条  从第二条开始（第一条标志为0） 此时表示查找第二第三条
                // 以上方法可以任意组合
                for (Book book2 : books) {
                    Log.e("MainActivity","book id is:" + book2.getId());
                    Log.e("MainActivity","book name is:" + book2.getName());
                    Log.e("MainActivity","book author is:" + book2.getAuthor());
                    Log.e("MainActivity","book press is:" + book2.getPress());
                    Log.e("MainActivity","book pags is:" + book2.getPags());
                    Log.e("MainActivity","book price is:" + book2.getPrice());
                    Log.e("MainActivity","------------------------------");
                }

                break;
        }
    }
}
