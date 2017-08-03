package com.wq.demo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiangwang on 7/25/17.
 */

public class DataServer {

    public static List<Item> getSampleData(int length) {
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Item item = new Item();
            item.setImg(url[i % url.length]);
            item.setName("Img " + i);
            list.add(item);
        }
        return list;
    }

    private static final String url[] = {
            "http://www.sucaitianxia.com/d/file/20131221/baaed054489f5352139e5077fd8ef4d9.png",
            "http://www.sucaitianxia.com/d/file/20131221/3445f1dd1caa959deeada0f62ac1dcc8.png",
            "http://www.sucaitianxia.com/d/file/20131221/a90d8e6fce7f55604da2f71a8d7eeb98.png",
            "http://www.sucaitianxia.com/d/file/20131221/244379b00766f9014a57a31563c9b198.png",
            "http://www.sucaitianxia.com/d/file/20131221/98aa8a67009f0385a0f366a2ad8a2d9a.png",
            "http://www.sucaitianxia.com/d/file/20131221/2c87fde52b35bae7fdbbb2f57d1c98ab.png",
            "http://www.sucaitianxia.com/d/file/20131221/13a00e7eb7572a68f320a31ffcac5ad5.png",
            "http://www.sucaitianxia.com/d/file/20131221/0537b22da2d3a06dbcec495bf06ca0e9.png",
            "http://www.sucaitianxia.com/d/file/20131221/bd1f3d1a11cfae33615a96ae14806a9c.png",
            "http://www.sucaitianxia.com/d/file/20131221/be7ef8ceb0de14164ccdd3e7ff25c3fc.png",
            "http://www.sucaitianxia.com/d/file/20131221/51d4a5c41bd79f25c8f7f08a31d0f6c7.png",
            "http://www.sucaitianxia.com/d/file/20131221/04ab8fa3ebcd7f82a5186ad23e05746f.png",
            "http://www.sucaitianxia.com/d/file/20131221/8db07daa0ad773a770b45672140263dd.png",
            "http://www.sucaitianxia.com/d/file/20131221/07d6034616282ea6f0fffaefd5fa6411.png",
            "http://www.sucaitianxia.com/d/file/20131221/8d2f2df6de36148899c2699464599707.png",
            "http://www.sucaitianxia.com/d/file/20131221/eefe068c3c351deb1f2ef748f4a9d901.png",

            "http://www.sucaitianxia.com/d/file/20131221/61280bd794e5012a217b6fe4a925d99b.png",
            "http://www.sucaitianxia.com/d/file/20131221/f3b8342870a17a85c9538041dc25a597.png",
            "http://www.sucaitianxia.com/d/file/20131221/1eac92c829101c31c9f8f574294dac30.png",
            "http://www.sucaitianxia.com/d/file/20131221/ca03f2b1fae930572f7c86005c78d22a.png",
            "http://www.sucaitianxia.com/d/file/20131221/aed84e1fd663181be0eca7905aaceb46.png",
            "http://www.sucaitianxia.com/d/file/20131221/4d656dd5db44adb4f460b3467d41dfff.png",
            "http://www.sucaitianxia.com/d/file/20131221/9b462830b83efe15657efc3d4d4d4552.png",
            "http://www.sucaitianxia.com/d/file/20131221/7f0c18c220d5f245994dbb306c7d3e13.png",
            "http://www.sucaitianxia.com/d/file/20131221/80c2eff18ba5c67e766e3adede5b5b7b.png",
            "http://www.sucaitianxia.com/d/file/20131221/40b9f0ac8a39dea94bba8e78226ffb57.png",
            "http://www.sucaitianxia.com/d/file/20131221/8a7441302d239812a9db5ad725c7257e.png",
            "http://www.sucaitianxia.com/d/file/20131221/4b870e42cf1b43debd706b98aefbe254.png",
            "http://www.sucaitianxia.com/d/file/20131221/a7a457ca4e8ca6db2f92ced6ab2bc6ad.png",
            "http://www.sucaitianxia.com/d/file/20131221/6a8e34847d9f78589ea10ed70cb26a5a.png",
            "http://www.sucaitianxia.com/d/file/20131221/7981f4f9e93dbd17d6580c96c50aaaf2.png",
            "http://www.sucaitianxia.com/d/file/20131221/f55b278fafe321404f9040d2f3cfa9d1.png",
            "http://www.sucaitianxia.com/d/file/20131221/97d35d5341efd8ed965e9a8099bd6c6d.png",
            "http://www.sucaitianxia.com/d/file/20131221/9d9eebaa2b3bc74638201a709d2b8331.png",
            "http://www.sucaitianxia.com/d/file/20131221/cda78213473c31e51358c3b7c709ffc4.png",
            "http://www.sucaitianxia.com/d/file/20131221/be4fcd22483cf6e4f32b056b88783ba8.png",
    };
}
