
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MongodbTest {

    private MongoCollection<Document> spit;

    @Before
    public void init() {
        //创建Mongodb的客户端
        MongoClient mongoClient = new MongoClient("192.168.200.133");

        //获取使用数据库
        MongoDatabase spitdb = mongoClient.getDatabase("spitdb");

        //获取使用的集合(相当于MySQL的数据库表)
        spit = spitdb.getCollection("spit");
    }


    @Test
    public void test1() {

        //对集合进行查询
        FindIterable<Document> documents = spit.find();

        //遍历查询的结果,打印数据
        for (Document document : documents) {
            System.out.println("-------------------");
            //System.out.println("主键_id:" + document.getString("_id"));
            //System.out.println("内容content:" + document.getString("content"));
            //System.out.println("用户userid:" + document.getString("userid"));
            //System.out.println("昵称nickname:" + document.getString("nickname"));
            //
            //System.out.println("visits:" + document.getInteger("visits"));

            System.out.println(document.toString());
        }

        //关闭客户端
        //mongoClient.close();
    }

    @Test
    public void test2() {

        //声明查询条件
        //BasicDBObject bson = new BasicDBObject("userid", "1014");
        BasicDBObject bson = new BasicDBObject("visits", new BasicDBObject("$gt", 1000));

        //对集合进行查询
        FindIterable<Document> documents = spit.find(bson);
        //FindIterable<Document> documents = spit.find().limit(2);

        //遍历查询的结果,打印数据
        for (Document document : documents) {
            System.out.println("-------------------");
            System.out.println("主键_id:" + document.getString("_id"));
            System.out.println("内容content:" + document.getString("content"));
            System.out.println("用户userid:" + document.getString("userid"));
            System.out.println("昵称nickname:" + document.getString("nickname"));

            System.out.println("visits:" + document.getInteger("visits"));
        }

        //关闭客户端
        //mongoClient.close();
    }

    @Test
    public void test3() {
        Document document = new Document("countent", "使用java代码操作mongodb");

        spit.insertOne(document);
    }

    //坑
    @Test
    public void test4() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", "使用java代码操作mongodb123");
        map.put("userid", "6666");
        map.put("visits", 3000);


        Document document = new Document(map);

        //BasicDBObject bson = new BasicDBObject("$set", new BasicDBObject("nickname", "张三"));
        BasicDBObject bson = new BasicDBObject("$set", new BasicDBObject(map));
        spit.updateOne(new BasicDBObject("_id", "3"), bson);
    }

    @Test
    public void test5() {


        spit.deleteOne(new BasicDBObject("countent", "使用java代码操作mongodb"));
    }
}
