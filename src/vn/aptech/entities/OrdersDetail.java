/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chuva
 */
@Entity
@Table(name = "orders_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdersDetail.findAll", query = "SELECT o FROM OrdersDetail o")
    , @NamedQuery(name = "OrdersDetail.findById", query = "SELECT o FROM OrdersDetail o WHERE o.id = :id")
    , @NamedQuery(name = "OrdersDetail.findBySku", query = "SELECT o FROM OrdersDetail o WHERE o.sku = :sku")
    , @NamedQuery(name = "OrdersDetail.findByPrice", query = "SELECT o FROM OrdersDetail o WHERE o.price = :price")
    , @NamedQuery(name = "OrdersDetail.findByDiscount", query = "SELECT o FROM OrdersDetail o WHERE o.discount = :discount")
    , @NamedQuery(name = "OrdersDetail.findByQuantity", query = "SELECT o FROM OrdersDetail o WHERE o.quantity = :quantity")
    , @NamedQuery(name = "OrdersDetail.findByCreatedDate", query = "SELECT o FROM OrdersDetail o WHERE o.createdDate = :createdDate")
    , @NamedQuery(name = "OrdersDetail.findByModifiedDate", query = "SELECT o FROM OrdersDetail o WHERE o.modifiedDate = :modifiedDate")})
public class OrdersDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "sku")
    private String sku;
    @Basic(optional = false)
    @Column(name = "price")
    private int price;
    @Basic(optional = false)
    @Column(name = "discount")
    private int discount;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Lob
    @Column(name = "content")
    private String content;
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orders orderId;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product productId;

    public OrdersDetail() {
    }

    public OrdersDetail(Long id) {
        this.id = id;
    }

    public OrdersDetail(Long id, String sku, int price, int discount, int quantity) {
        this.id = id;
        this.sku = sku;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersDetail)) {
            return false;
        }
        OrdersDetail other = (OrdersDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.entities.OrdersDetail[ id=" + id + " ]";
    }
    
}
