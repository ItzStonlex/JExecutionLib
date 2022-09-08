# JExecutionLib
Execute functions and another Java code with help more utilities

---

This library provides many tools for configurability 
and initialization flexibility to run your code or project.

---

## FEEDBACK

- My Discord Server: **[Link](https://discord.gg/GmT9pUy8af)**
- My VKontakte Page: **[Link](https://vk.com/itzstonlex)**

---

## HOW TO USE?

**FULL DOCUMENTATION LINK: [[Wiki Page]](https://github.com/ItzStonlex/JExecutionLib/wiki)**

---

An simple example of initializing your<br>
project for the requirements of this library:

```java
import com.itzstonlex.executionlib.ExecutionAppContext;

public class Bootstrap {

    public static void main(String[] args) {
        ExecutionAppContext.runServices(Bootstrap.class);
    }
}
```


An example of initializing your project for<br>
the requirements of this library **with a configuration**:
```java
import com.itzstonlex.executionlib.ExecutionAppConfiguration;
import com.itzstonlex.executionlib.ExecutionAppContext;

public class Bootstrap {

    public static void main(String[] args) {
        ExecutionAppConfiguration configuration = new ExecutionAppConfiguration();
        // todo - configuration management.

        ExecutionAppContext.runServices(Bootstrap.class, configuration);
    }
}
```

---

## PLEASE, SUPPORT ME


By clicking on this link, you can support me as a 
developer and motivate me to develop new open-source projects!

<a href="https://www.buymeacoffee.com/itzstonlex" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: 41px !important;width: 174px !important;box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;-webkit-box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;" ></a>
