[TOC]



#### annotation

---

###### `@Resource` and `@Autowired`

`@Resource` 

+ jdk since 1.6 support

+ auto create bean by bean name default, and use bean type if not found same name. 

+ if user assigned bean name, `@Resource` only found corresponding name to build bean. it will be throw Exception while not found.

  example:

  ```java
  package org.personal.blackcat.service;
  
  
  public interface ShowService {
      void show();
  }
  
  ```

  ```java
  package org.personal.blackcat.service.impl;
  
  import org.springframework.stereotype.Service;
  import lombok.extern.slf4j.Slf4j;
  
  @Slf4j
  @Service
  public class ShowServiceImpl implements ShowService {
      public void show() {
          log.info("{}", "this is an example");
      }
  }
  ```

  `@Service` not assigned attribute `name` , default is bean name like `showServiceImpl`.

  ```java
  package org.personal.blackcat.web.controller;
  
  import javax.annotation.Resource;
  
  public class ShowController {
      
      @Resource(name="showService")
      private ShowService showService;
      
  }
  
  ```

`@Autowired`

+ spring support
+ default use bean type to build bean, then assigned attribute `required=false` can ignore  dependent object null check.
+ `@Autowired` with `@Qualifier("${beanName}")` like `@Resource`, but Not sure that they have the same characteristics.

#### Dependency Injection

---

###### Question

it always received `NullPointerException` while start spring application,  debug  => find the reason => a bean with `@Resource` or `@Autowired` is null.  why?

example:

```java
package org.personal.blackcat.service;

import org.springframework.stereotype.Service;

@Service
public interface CommonService {
    
}
```

```java
package org.personal.blackcat.service;

import org.springframework.stereotype.Service;

@Service
public interface TimeService {
    
    String formatDate(Date date, String pattern);
    
    Date formatDate(String date, String pattern);
}
```

```java
package org.personal.blackcat.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private TimeService timeService;
    
    pubilc String print() {
       return timeService.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }
}
```

```java
package org.personal.blackcat.main;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Launcher {
    public static void main(String[] args) {
        UserService userService = new UserService();
        log.info("{}", userService.print());
    }
}
```

`timeService = null` ,  it transfer `formatDate()` will throw `NullPointerException`.

Because `UserService` is not created by Spring, so `TimeService` also will not created by Spring. 

