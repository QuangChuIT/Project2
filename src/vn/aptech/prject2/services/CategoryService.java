/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.prject2.services;

import java.util.Date;
import javax.persistence.EntityManager;
import vn.aptech.entities.Category;
import vn.aptech.project2.utils.JPAUtils;

public class CategoryService extends AbstractFacade<Category>{

    
    public CategoryService() {
        super(Category.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return JPAUtils.getInstance().getEntityManager();
    }

    public static void main(String[] args) {
        Category category = new Category();
        category.setId(4L);
        category.setTitle("Test category 111");
        category.setSlug("test-category");
        category.setCreatedDate(new Date());
        category.setModifiedDate(new Date());
        CategoryService service = new CategoryService();
        Category cate1 = service.find(4L);
        service.remove(cate1);
    }
}
