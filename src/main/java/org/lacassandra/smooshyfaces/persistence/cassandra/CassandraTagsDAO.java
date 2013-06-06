package org.lacassandra.smooshyfaces.persistence.cassandra;

import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.data.DataAPI;
import io.cassandra.sdk.data.DataBulkModel;
import io.cassandra.sdk.data.DataColumn;
import io.cassandra.sdk.data.DataMapModel;
import io.cassandra.sdk.data.DataRowkey;
import io.cassandra.sdk.exception.CassandraIoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.lacassandra.smooshyfaces.entity.Book;
import org.lacassandra.smooshyfaces.persistence.BookDAO;

import com.google.common.base.Joiner;
import com.netflix.astyanax.serializers.AnnotatedCompositeSerializer;

public class CassandraTagsDAO  implements BookDAO {

	private static Logger log = Logger.getLogger(CassandraTagsDAO.class);
	
	private DataAPI dataAPI; 
	private String keySpaceName;
	private String columnFamilyName;
	
    public final static String COLUMN_NAME_TAGS = "tags";
    public final static String COLUMN_NAME_ISBN = "isbn";
    public final static String COLUMN_NAME_TITLE = "tags";
    public final static String COLUMN_NAME_SAMPLER = "sampler";

    protected final static AnnotatedCompositeSerializer<BookCommentEntry> BOOK_COMMENT_SERIALIZER =
            new AnnotatedCompositeSerializer<BookCommentEntry>(BookCommentEntry.class);

    @Override
    public Book findById(final UUID id) {
        // You figure this one out :)
        throw new NotImplementedException();
    }

    /**
     * 
     * @param isbn
     * @return
     */
    public Book findById(final String isbn)  {
    	
    	Book b = null;
    			
    	if (isbn == null) {
            return null;
        }
    	try{
    		
    		DataMapModel data = dataAPI.getData(this.keySpaceName, this.columnFamilyName, isbn ,0, null);
    		
    		b = convert(data);
    		
    	}
    	catch(CassandraIoException e){
    		log.error(e.getMessage());
    	}
		
		return b;
    }

    @Override
    public Book save(final Book entity) {

    	List<DataColumn> columns = new ArrayList<DataColumn>();
    	
    	String tags = Joiner.on(",").join(entity.getTags());
    	
		columns.add(new DataColumn(COLUMN_NAME_TAGS, tags));
		columns.add(new DataColumn(COLUMN_NAME_ISBN, entity.getIsbn()));
		columns.add(new DataColumn(COLUMN_NAME_TITLE, entity.getIsbn()));
		columns.add(new DataColumn(COLUMN_NAME_SAMPLER, entity.getSampler()));

		List<DataRowkey> rows = new ArrayList<DataRowkey>();
		
		DataRowkey row = new DataRowkey(entity.getIsbn(), columns);
		rows.add(row);

		DataBulkModel dataBulk = new DataBulkModel(rows);

		try {
			StatusMessageModel sm = dataAPI.postBulkData(this.keySpaceName, this.columnFamilyName, dataBulk);
			log.info(sm.getMessage());
		}
		catch(CassandraIoException e){
			log.error(e.getMessage());
		}
    	
    	return entity;
    }

    
    @Override
	public Long countAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findByIds(Collection<UUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> save(List<Book> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Book entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public List<Book> findByTag(final String tag) {
        // You figure this one out :)
        throw new NotImplementedException();
    }

    @Override
    public List<Book> findByTag(final String tag, final UUID start, final int pageSize) {
        // You figure this one out :)
        throw new NotImplementedException();
    }

	public DataAPI getDataAPI() {
		return dataAPI;
	}

	public void setDataAPI(DataAPI dataAPI) {
		this.dataAPI = dataAPI;
	}
	/**
	 * 
	 * @param data
	 * @return
	 */
	private Book convert(DataMapModel data){
		
        Book book = new Book();
        
        String t = data.get(COLUMN_NAME_TAGS);
        
        List<String> tList = Arrays.asList(t.split(","));
        
        book.setTags(tList);
        book.setIsbn(data.get(COLUMN_NAME_ISBN));
        book.setSampler(data.get(COLUMN_NAME_SAMPLER));
        book.setTitle(data.get(COLUMN_NAME_TITLE));
        
        return book;
	}

	public String getKeySpaceName() {
		return keySpaceName;
	}

	public void setKeySpaceName(String keySpaceName) {
		this.keySpaceName = keySpaceName;
	}

	public String getColumnFamilyName() {
		return columnFamilyName;
	}

	public void setColumnFamilyNAme(String columnFamilyName) {
		this.columnFamilyName = columnFamilyName;
	}
    
}
