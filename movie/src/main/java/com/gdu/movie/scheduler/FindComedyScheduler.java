package com.gdu.movie.scheduler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.movie.dao.MovieMapper;
import com.gdu.movie.dto.MovieDto;

@EnableScheduling
@Component
public class FindComedyScheduler {

	@Autowired
	private MovieMapper movieMapper;
	
	@Scheduled(cron = "0 0/1 * * * ?")
	public void execute() throws Exception {
		
	  Map<String, Object> map = Map.of("column", "GENRE", "searchText", "코미디");
		
		List<MovieDto> movies = movieMapper.getMovieSearchResult(map);
		
		if(movies.isEmpty()) {
			try (PrintWriter out = new PrintWriter(new File("error.txt"))) {
				out.println("코미디 검색 결과가 없습니다.");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try (PrintWriter out = new PrintWriter(new File("코미디.txt"))) {
				for (MovieDto movie : movies) {
					out.println("제목 : " + movie.getTitle());
					out.println("장르 : " + movie.getGenre());
					out.println("개요 : " + movie.getDescription());
					out.println("평점 : " + movie.getStar());
				}
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
