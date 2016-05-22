package nl.hu.fnt.gsos.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.hu.fnt.gsos.service.ServiceProvider;
import nl.hu.fnt.gsos.service.Track;
import nl.hu.fnt.gsos.service.TrackServiceImpl;
 
@Path("/")
public class RestService {
 
	@GET
	@Path("/tracks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTracks() {
		TrackServiceImpl service = ServiceProvider.getTrackService(); 
		return Response.ok(service.getTracks()).build();
	}
	
	@GET
	@Path("/tracks/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrackCount(){
		int count = ServiceProvider.getTrackService().getTracks().size();
		return Response.ok(count).build();
	}
	
	@GET
	@Path("/tracks/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrackByID(@PathParam("id") int trackID){	
		return Response.ok(ServiceProvider.getTrackService().getTrackById(trackID)).build();
	}
	
	@PUT
	@Path("/tracks/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTrack(@PathParam("id")int trackID, @QueryParam("artist") String artist, @QueryParam("song") String song, @QueryParam("year") int year, @QueryParam("url") String URL){
		
		List<Track> tracks = ServiceProvider.getTrackService().getTracks();
		tracks.add(new Track(trackID, artist, song, year, URL));
		
		ServiceProvider.getTrackService().setTracks(tracks);
		
		return Response.ok(tracks).build();
	}
	
	@DELETE
	@Path("/tracks/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTrack(@PathParam("id") int trackID){
		
		List<Track> tracks = ServiceProvider.getTrackService().getTracks();
		Track currentTrack = null;
		for(Track track : tracks){
			if(track.getId() == trackID){
				currentTrack = track;
			}
		}
		
		tracks.remove(currentTrack);
		
		ServiceProvider.getTrackService().setTracks(tracks);
		
		return Response.ok(tracks).build();
	}
 
}