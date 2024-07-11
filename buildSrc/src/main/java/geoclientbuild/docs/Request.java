package geoclientbuild.docs;

import java.util.Map;
public class Request {

    private String id;
    private String type;
    private Map<String, String> parameters;

    public Request() {

    }

    /**
     * @param id
     * @param type
     * @param parameters
     */
    public Request(String id, String type, Map<String, String> parameters) {
        this.id = id;
        this.type = type;
        this.parameters = parameters;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return the parameters
     */
    public Map<String, String> getParameters() {
        return parameters;
    }
    public boolean hasParameters() {
        return parameters != null && !parameters.isEmpty();
    }
    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Request [id=" + id + ", type=" + type + ", parameters=" + parameters + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Request other = (Request) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (parameters == null) {
            if (other.parameters != null)
                return false;
        } else if (!parameters.equals(other.parameters))
            return false;
        return true;
    }


}
