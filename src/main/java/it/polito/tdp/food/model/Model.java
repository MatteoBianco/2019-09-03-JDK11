package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<String, DefaultWeightedEdge> graph;
	private List<PortionNamePair> edges;
	
	private List<String> bestPath;
	private Integer bestWeight;
	private Integer pathLength;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void createGraph(Integer calories) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.edges = new ArrayList<>();
		
		Graphs.addAllVertices(this.graph, this.dao.listPortionNameForCalories(calories));
		this.edges = this.dao.listPortionNamePairsForCalories(calories);
		for(PortionNamePair pnp : this.edges) {
			if(this.graph.getEdge(pnp.getPn1(), pnp.getPn2()) == null &&
					this.graph.containsVertex(pnp.getPn1()) && this.graph.containsVertex(pnp.getPn2())) {
				Graphs.addEdge(this.graph, pnp.getPn1(), pnp.getPn2(), pnp.getWeight());
			}
		}
	}
	
	public List<String> bestPath(String start, Integer N) {
		this.bestPath = new ArrayList<>();
		this.bestWeight = 0;
		this.pathLength = N;
				
		List<String> parziale = new ArrayList<>();
		parziale.add(start);
		this.recursion(parziale, 0, start);
		return this.bestPath;
	}
	
	public Integer bestWeight() {
		return bestWeight;
	}	
	
	private void recursion(List<String> parziale, Integer weight, String lastVertex) {
		if(parziale.size() == this.pathLength) {
			if(weight > this.bestWeight) {
				this.bestPath = new ArrayList<>(parziale);
				this.bestWeight = weight;
			}
			return;
		}
		
		for(String s : Graphs.neighborListOf(this.graph, lastVertex)) {
			if(!parziale.contains(s)) {
				weight += (int) this.graph.getEdgeWeight(this.graph.getEdge(lastVertex, s));
				parziale.add(s);
				recursion(parziale, weight, s);
				weight -= (int) this.graph.getEdgeWeight(this.graph.getEdge(lastVertex, s));
				parziale.remove(parziale.size() - 1);
			}
		}
	}

	public Integer vertexSize() {
		if(this.graph != null)
			return this.graph.vertexSet().size();
		return null;
	}
	
	public Integer edgeSize() {
		if(this.graph != null)
			return this.graph.edgeSet().size();
		return null;
	}
	
	public List<String> getAllVertices() {
		if(this.graph != null)
			return new ArrayList<>(this.graph.vertexSet());
		return null;
	}
	
	public List<String> getNeighborsOf(String portionName) {
		return Graphs.neighborListOf(this.graph, portionName);
	}
	
	
	
}
