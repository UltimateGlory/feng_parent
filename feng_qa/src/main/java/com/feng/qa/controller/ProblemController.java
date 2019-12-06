package com.feng.qa.controller;

import com.feng.qa.client.LabelClient;
import com.feng.qa.pojo.Problem;
import com.feng.qa.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pojo.PageResult;
import pojo.Result;
import pojo.StatusCode;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;


	@Autowired
	private LabelClient labelClient;

	@RequestMapping(value = "/newlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result findNewProblemList(@PathVariable String labelid, @PathVariable int page,@PathVariable int size){
		Page<Problem> pageDate = problemService.findNewProblemList(labelid,page,size);
		return new Result(true, StatusCode.OK,"查询成功",new PageResult<Problem>(pageDate.getTotalElements(),
				pageDate.getContent()));
	}
	@RequestMapping(value = "/hotlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result findHotProblemList(@PathVariable String labelid, @PathVariable int page,@PathVariable int size){
		Page<Problem> pageDate = problemService.findHotProblemList(labelid,page,size);
		return new Result(true, StatusCode.OK,"查询成功",new PageResult<Problem>(pageDate.getTotalElements(),
				pageDate.getContent()));
	}

	@RequestMapping(value = "/waitlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result findWaitReplyList(@PathVariable String labelid, @PathVariable int page,@PathVariable int size){
		Page<Problem> pageDate = problemService.findWaitReplyList(labelid,page,size);
		return new Result(true, StatusCode.OK,"查询成功",new PageResult<Problem>(pageDate.getTotalElements(),
				pageDate.getContent()));
	}

	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 根绝
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/label/{id}",method = RequestMethod.GET)
	public Result findLabelById(@PathVariable("id") String id){
		Result result = labelClient.findLabelById(id);
           return result;
	}

}
