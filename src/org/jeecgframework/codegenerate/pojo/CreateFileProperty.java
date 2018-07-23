//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.codegenerate.pojo;

public class CreateFileProperty {
    private boolean actionFlag = true;
    private boolean serviceIFlag = true;
    private boolean entityFlag = true;
    private boolean pageFlag = true;
    private boolean serviceImplFlag = true;
    private boolean jspFlag = true;
    
    private String jspMode = "01";

    public CreateFileProperty() {
    }

    public boolean isActionFlag() {
        return this.actionFlag;
    }

    public boolean isServiceIFlag() {
        return this.serviceIFlag;
    }

    public boolean isEntityFlag() {
        return this.entityFlag;
    }

    public boolean isPageFlag() {
        return this.pageFlag;
    }

    public boolean isServiceImplFlag() {
        return this.serviceImplFlag;
    }

    public void setActionFlag(boolean actionFlag) {
        this.actionFlag = actionFlag;
    }

    public void setServiceIFlag(boolean serviceIFlag) {
        this.serviceIFlag = serviceIFlag;
    }

    public void setEntityFlag(boolean entityFlag) {
        this.entityFlag = entityFlag;
    }

    public void setPageFlag(boolean pageFlag) {
        this.pageFlag = pageFlag;
    }

    public void setServiceImplFlag(boolean serviceImplFlag) {
        this.serviceImplFlag = serviceImplFlag;
    }

    public boolean isJspFlag() {
        return this.jspFlag;
    }

    public void setJspFlag(boolean jspFlag) {
        this.jspFlag = jspFlag;
    }

	public String getJspMode() {
		return jspMode;
	}

	public void setJspMode(String jspMode) {
		this.jspMode = jspMode;
	}

}
