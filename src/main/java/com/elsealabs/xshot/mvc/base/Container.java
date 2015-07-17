package com.elsealabs.xshot.mvc.base;

public class Container {
	
	private Model      model;
	private View       view;
	private Controller controller;
	
	public void start()
	{
		model.start();
		view.define();
		
		controller.linkModel(model);
		controller.linkView(view);
		controller.init();
		
		view.setVisible(true);
	}
	
	public void end()
	{
		view.setVisible(false);
		view.dispose();
		
		model.end();
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public Model getModel() {
		return model;
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public View getView() {
		return view;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public Controller getController() {
		return controller;
	}
}