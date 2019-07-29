package com.ziwu.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Menu {
	private int id;
	private String url;
	private String path;
	private Object component;
	private String name;
	private int parentId;
	private List<Role> roles;
	private List<Menu> children;
	private MenuMeta meta;
	
	
	public MenuMeta getMeta() {
		return meta;
	}
	public void setMeta(MenuMeta meta) {
		this.meta = meta;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@JsonIgnore
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@JsonFormat(shape=JsonFormat.Shape.OBJECT)
	public Object getComponent() {
		return component;
	}
	public void setComponent(Object component) {
		this.component = component;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	@JsonIgnore
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
}
