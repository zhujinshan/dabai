package com.dabai.proxy.httpclient.spring;

import com.dabai.proxy.httpclient.core.GlobalContext;
import com.dabai.proxy.httpclient.core.HttpRequestHandler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * @author zhujinshan
 */
public class HttpClientProxyFactoryBean<T> implements FactoryBean<T>, InitializingBean, DisposableBean, ResourceLoaderAware {

    private Class<?> beanInterface;

    private String beanInterfaceName;

    private ResourceLoader resourceLoader;

    //TODO 把这里变为可配置的
    private boolean cglib = false;

    private boolean enableAsyncClient = false;

    public HttpClientProxyFactoryBean() {
        super();
    }

    public void setBeanInterfaceName(String beanInterfaceName) {
        this.beanInterfaceName = beanInterfaceName;
    }

    public void setEnableAsyncClient(boolean enableAsyncClient) {
        this.enableAsyncClient = enableAsyncClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        beanInterface = resourceLoader.getClassLoader().loadClass(beanInterfaceName);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void destroy() throws Exception {
        GlobalContext.getInstance().close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() throws Exception {
        if (cglib) {

            //使用CGLIB代理
            Enhancer enhancer = new Enhancer();
            if (beanInterface.isInterface()) {
                enhancer.setInterfaces(new Class<?>[]{beanInterface});
            } else {
                enhancer.setSuperclass(beanInterface);
            }
            enhancer.setCallback(new HttpClientCglibProxy());
            return (T) enhancer.create();

        } else {
            //使用JDK代理
            if (beanInterface.isInterface()) {
                return (T) Proxy.newProxyInstance(resourceLoader.getClassLoader(), new Class<?>[]{beanInterface},
                        new HttpClientJdkProxy());
            } else {
                //如果不是接口，无法使用JDK代理
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(beanInterface);
                enhancer.setCallback(new HttpClientCglibProxy());

                return (T) enhancer.create();
            }

        }

    }

    @Override
    public Class<?> getObjectType() {
        return beanInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    class HttpClientCglibProxy implements MethodInterceptor {

        private HttpRequestHandler handler;

        public HttpClientCglibProxy() {
            this.handler = new HttpRequestHandler(beanInterface, enableAsyncClient);
        }

        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (!Modifier.isAbstract(method.getModifiers())) {
                if (method.isDefault()) {
                    // return method.invoke(target, args);
                    return proxy.invokeSuper(target, args);
                } else {
                    return proxy.invokeSuper(target, args);
                }
            } else {
                return handler.invoke(method, args);
            }
        }

    }

    class HttpClientJdkProxy implements InvocationHandler {

        private HttpRequestHandler handler;

        public HttpClientJdkProxy() {
            this.handler = new HttpRequestHandler(beanInterface, enableAsyncClient);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return handler.invoke(method, args);
        }

    }

}
