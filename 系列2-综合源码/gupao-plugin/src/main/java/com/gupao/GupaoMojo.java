package com.gupao;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

/**
 * Created by James on 2017/4/23.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
@Mojo(name = "gupaoedu", defaultPhase = LifecyclePhase.PACKAGE)
public class GupaoMojo extends AbstractMojo {

    @Parameter
    private String msg;

    @Parameter
    private List<String> options;

    @Parameter(property = "args")
    private String args;

    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("gupaoedu plugin  !!!" + msg);
        System.out.println("gupaoedu plugin  !!!" + options);
        System.out.println("gupaoedu plugin  !!!" + args);
    }
}
