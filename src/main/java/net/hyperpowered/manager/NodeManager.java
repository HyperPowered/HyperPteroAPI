package net.hyperpowered.manager;

import net.hyperpowered.logger.PteroLogger;
import net.hyperpowered.node.*;
import net.hyperpowered.node.builder.NodeBuilder;
import net.hyperpowered.requester.ApplicationEndpoint;
import net.hyperpowered.server.builder.AllocationBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class NodeManager extends Manager {

    private static final PteroLogger LOGGER = new PteroLogger("NODES");

    public CompletableFuture<List<Node>> listNodes() {
        CompletableFuture<List<Node>> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NODES.getEndpoint()).thenAccept(responseJson -> {
            List<Node> nodes = new ArrayList<>();
            try {
                JSONObject responseObject = (JSONObject) responseJson.get("response");
                JSONArray nodesJson = (JSONArray) responseObject.get("data");
                for (Object nodeDetails : nodesJson) {
                    Node node = parseNode(nodeDetails.toString());
                    nodes.add(node);
                }
            } catch (Exception e) {
                response.completeExceptionally(e);
            }

            response.complete(nodes);
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR OS NODES: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<List<Allocation>> getAllocationByPort(long nodeID, int port) {
        CompletableFuture<List<Allocation>> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NODES.getEndpoint() + "/" + nodeID + "/allocations?filter[port]=" + port).thenAccept(responseJson -> {
            List<Allocation> allocations = new ArrayList<>();
            try {
                JSONObject responseObject = (JSONObject) responseJson.get("response");
                JSONArray allocationJson = (JSONArray) responseObject.get("data");

                for (Object allocationDetails : allocationJson) {
                    Allocation allocation = parseAllocation(allocationDetails.toString());
                    allocations.add(allocation);
                }
            } catch (Exception e) {
                response.completeExceptionally(e);
            }

            response.complete(allocations);
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR AS PORTAS: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<List<Allocation>> getAllocationByIP(long nodeID, String ip, int port) {
        CompletableFuture<List<Allocation>> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NODES.getEndpoint() + "/" + nodeID + "/allocations?filter[port]=" + port+"&filter[ip]="+ip).thenAccept(responseJson -> {
            List<Allocation> allocations = new ArrayList<>();
            try {
                JSONObject responseObject = (JSONObject) responseJson.get("response");
                JSONArray allocationJson = (JSONArray) responseObject.get("data");

                for (Object allocationDetails : allocationJson) {
                    Allocation allocation = parseAllocation(allocationDetails.toString());
                    allocations.add(allocation);
                }
            } catch (Exception e) {
                response.completeExceptionally(e);
            }

            response.complete(allocations);
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR AS PORTAS: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<List<Allocation>> listAllocations(long nodeID) {
        CompletableFuture<List<Allocation>> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NODES.getEndpoint() + "/" + nodeID + "/allocations").thenAccept(responseJson -> {
            List<Allocation> allocations = new ArrayList<>();
            try {
                JSONObject responseObject = (JSONObject) responseJson.get("response");
                JSONArray allocationJson = (JSONArray) responseObject.get("data");
                JSONObject meta = (JSONObject) responseObject.get("meta");
                JSONObject pagination = (JSONObject) meta.get("pagination");
                long pages = (long) pagination.get("total_pages");

                for (int i = 1; i < pages; i++) {
                    List<Allocation> allcs = listAllocations(nodeID, (i + 1)).get();
                    allocations.addAll(allcs);
                }

                for (Object allocationDetails : allocationJson) {
                    Allocation allocation = parseAllocation(allocationDetails.toString());
                    allocations.add(allocation);
                }
            } catch (Exception e) {
                response.completeExceptionally(e);
            }

            response.complete(allocations);
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR AS PORTAS: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<List<Allocation>> listAllocations(long nodeID, int page) {
        CompletableFuture<List<Allocation>> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NODES.getEndpoint() + "/" + nodeID + "/allocations?page=" + page).thenAccept(responseJson -> {
            List<Allocation> allocations = new ArrayList<>();
            try {
                JSONObject responseObject = (JSONObject) responseJson.get("response");
                JSONArray allocationJson = (JSONArray) responseObject.get("data");
                for (Object allocationDetails : allocationJson) {
                    Allocation allocation = parseAllocation(allocationDetails.toString());
                    allocations.add(allocation);
                }
            } catch (Exception e) {
                response.completeExceptionally(e);
            }

            response.complete(allocations);
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR AS PORTAS: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<Integer> getAllocationCount(long nodeID) {
        CompletableFuture<Integer> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NODES.getEndpoint() + "/" + nodeID + "/allocations").thenAccept(responseJson -> {
            try {
                JSONObject responseObject = (JSONObject) responseJson.get("response");
                JSONObject meta = (JSONObject) responseObject.get("meta");
                JSONObject pagination = (JSONObject) meta.get("pagination");
                int total = Math.toIntExact((long) pagination.get("total"));
                response.complete(total);
            } catch (Exception e) {
                response.completeExceptionally(e);
            }
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR AS PORTAS: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<Node> getNode(long nodeID) {
        CompletableFuture<Node> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NODES.getEndpoint() + "/" + nodeID).thenAccept(responseJson -> {
            try {
                JSONObject responseNodeJson = (JSONObject) responseJson.get("response");
                response.complete(parseNode(responseNodeJson.toString()));
            } catch (Exception e) {
                response.completeExceptionally(e);
            }
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR O NODE: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<NodeConfiguration> getNodeConfiguration(long nodeID) {
        CompletableFuture<NodeConfiguration> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NODES.getEndpoint() + "/" + nodeID + "/configuration").thenAccept(responseJson -> {
            try {
                JSONObject responseNodeJson = (JSONObject) responseJson.get("response");
                response.complete(parseNodeConfig(responseNodeJson.toString()));
            } catch (Exception e) {
                response.completeExceptionally(e);
            }
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR O NODE: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<JSONObject> createNode(NodeBuilder builder) {
        return create(builder, ApplicationEndpoint.NODES.getEndpoint()).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO CRIAR UM NODE: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public CompletableFuture<JSONObject> updateNode(Node node) {
        return update(ApplicationEndpoint.NODES.getEndpoint() + "/" + node.getId(), makeRequestJSON(node)).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO ATUALIZAR UM NODE: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public CompletableFuture<JSONObject> deleteNode(long nodeID) {
        return delete(ApplicationEndpoint.NODES.getEndpoint() + "/" + nodeID).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO DELETAR UM NODE: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public CompletableFuture<JSONObject> createAllocation(AllocationBuilder builder, long nodeID) {
        return create(builder, ApplicationEndpoint.NODES.getEndpoint() + "/" + nodeID + "/allocations").exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO CRIAR UMA PORTA: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public CompletableFuture<JSONObject> deleteAllocation(long nodeID, long allocationID) {
        return delete(ApplicationEndpoint.NODES.getEndpoint() + "/" + nodeID + "/allocations/" + allocationID).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO DELETAR UMA PORTA: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public NodeConfiguration parseNodeConfig(String nodeConfigJson) throws ParseException {
        JSONObject nodeConfigDetailsGeneral = (JSONObject) new JSONParser().parse(nodeConfigJson);
        JSONObject nodeConfigAPIJson = (JSONObject) nodeConfigDetailsGeneral.get("api");
        JSONObject nodeConfigSystem = (JSONObject) nodeConfigDetailsGeneral.get("system");
        JSONObject nodeConfigSSL = (JSONObject) nodeConfigAPIJson.get("ssl");
        SSL ssl = new SSL(
                (Boolean) nodeConfigSSL.get("enabled"),
                (String) nodeConfigSSL.get("cert"),
                (String) nodeConfigSSL.get("key")
        );

        NodeConfigApi configApi = new NodeConfigApi(
                (String) nodeConfigAPIJson.get("host"),
                (long) nodeConfigAPIJson.get("port"),
                ssl,
                (long) nodeConfigAPIJson.get("upload_limit")
        );

        NodeConfigSystem configSystem = new NodeConfigSystem(
                (String) nodeConfigSystem.get("data"),
                (JSONObject) nodeConfigSystem.get("sftp")
        );

        return new NodeConfiguration(
                (Boolean) nodeConfigDetailsGeneral.get("debug"),
                UUID.fromString((String) nodeConfigDetailsGeneral.get("uuid")),
                (String) nodeConfigDetailsGeneral.get("token_id"),
                (String) nodeConfigDetailsGeneral.get("token"),
                configApi,
                configSystem,
                (String) nodeConfigDetailsGeneral.get("remote")
        );
    }

    public Node parseNode(String nodeJson) throws ParseException {
        JSONObject nodeDetailsGeneral = (JSONObject) new JSONParser().parse(nodeJson);
        JSONObject nodeDetails = (JSONObject) nodeDetailsGeneral.get("attributes");
        return new Node(
                (long) nodeDetails.get("id"),
                UUID.fromString((String) nodeDetails.get("uuid")),
                (Boolean) nodeDetails.get("public"),
                (String) nodeDetails.get("name"),
                (String) nodeDetails.get("description"),
                (long) nodeDetails.get("location_id"),
                (String) nodeDetails.get("fqdn"),
                (String) nodeDetails.get("scheme"),
                (Boolean) nodeDetails.get("behind_proxy"),
                (Boolean) nodeDetails.get("maintenance_mode"),
                (long) nodeDetails.get("memory"),
                (long) nodeDetails.get("memory_overallocate"),
                (long) nodeDetails.get("disk"),
                (long) nodeDetails.get("disk_overallocate"),
                (long) nodeDetails.get("upload_size"),
                (long) nodeDetails.get("daemon_listen"),
                (long) nodeDetails.get("daemon_sftp"),
                (String) nodeDetails.get("daemon_base"),
                (String) nodeDetails.get("created_at"),
                (String) nodeDetails.get("updated_at")
        );
    }

    public Allocation parseAllocation(String allocationJson) throws ParseException {
        JSONObject allocationDetailsGeneral = (JSONObject) new JSONParser().parse(allocationJson);
        JSONObject allocationDetails = (JSONObject) allocationDetailsGeneral.get("attributes");
        return new Allocation(
                (long) allocationDetails.get("id"),
                (String) allocationDetails.get("ip"),
                (String) allocationDetails.get("alias"),
                (long) allocationDetails.get("port"),
                (String) allocationDetails.get("notes"),
                (boolean) allocationDetails.get("assigned")
        );
    }

    private JSONObject makeRequestJSON(Node node) {
        JSONObject response = new JSONObject();
        response.put("name", node.getName());
        response.put("description", node.getDescription());
        response.put("location_id", node.getLocation_id());
        response.put("fqdn", node.getFqdn());
        response.put("scheme", node.getScheme());
        response.put("behind_proxy", node.isBehind_proxy());
        response.put("maintence_mode", node.isMaintenance_mode());
        response.put("memory", node.getMemory());
        response.put("memory_overallocate", node.getMemory_overallocate());
        response.put("disk", node.getDisk());
        response.put("disk_overallocate", node.getDisk_overallocate());
        response.put("upload_size", node.getUpload_size());
        response.put("daemon_sftp", node.getDaemon_sftp());
        response.put("daemon_listen", node.getDaemon_listen());
        return response;
    }
}