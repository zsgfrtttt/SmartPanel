# SmartPanel
Android面板切换工具库，可用于表情面板，输入面板与系统软件盘的无缝切换

### 在根.gradle添加jitpack
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```
添加依赖
dependencies {
	       implementation 'com.github.zsgfrtttt:SmartPanel:1.0.0'
	}
```
# 基本使用
####
```
public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private PanelLinearLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.et);
        parent = findViewById(R.id.layout_content);
        parent.setKeyboardListener(new KeyboardOpListener() {
            @Override
            public void requestCloseKeyboard() {
                Util.hideKeyboard(editText);
            }
        });

    }

    public void onClickPanel(View view) {
        if (parent.isOpen()) {
            Util.showKeyboard(editText);
        } else {
            parent.openPanel();
        }
    }

    @Override
    public void onBackPressed() {
        if (parent.isOpen()) {
            parent.closePanel();
            return;
        }
        super.onBackPressed();
    }
}
```

# 联系方式 
如果你在使用SmartPanel过程中发现任何问题，你可以通过如下方式联系我：
* 邮箱: 1058079995@qq.com
